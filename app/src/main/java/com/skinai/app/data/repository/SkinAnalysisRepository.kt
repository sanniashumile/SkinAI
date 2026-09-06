package com.skinai.app.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.skinai.app.data.model.AnalysisSource
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.ScanResult
import com.skinai.app.data.offline.OfflineContentRepository
import com.skinai.app.ml.SkinToneAnalyzer
import com.skinai.app.ml.TFLiteClassifier
import com.skinai.app.network.GeminiClient
import com.skinai.app.util.ConnectivityObserver
import com.skinai.app.util.LanguageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

sealed class AnalysisOutcome {
    data class Success(val result: ScanResult) : AnalysisOutcome()
    data class Error(val message: String) : AnalysisOutcome()
}

/**
 * Decides whether to run analysis online (Gemini) or offline (on-device TFLite model)
 * based on real connectivity + user's force-offline preference, and returns a unified ScanResult.
 */
class SkinAnalysisRepository(
    private val context: Context,
    private val connectivityObserver: ConnectivityObserver,
    private val settingsStore: SettingsStore
) {
    // Cache loaded classifiers so we don't reload the model file on every scan
    private val classifierCache = mutableMapOf<ScanCategory, TFLiteClassifier>()

    private fun getClassifier(category: ScanCategory): TFLiteClassifier {
        return classifierCache.getOrPut(category) {
            TFLiteClassifier(
                context = context,
                modelAssetName = category.modelAsset,
                inputSize = category.inputSize,
                outputIsSoftmax = category.outputIsSoftmax
            )
        }
    }

    suspend fun analyze(
        bitmap: Bitmap,
        category: ScanCategory,
        imagePath: String
    ): AnalysisOutcome = withContext(Dispatchers.Default) {
        val forceOffline = settingsStore.forceOffline.first()
        val apiKey = settingsStore.apiKey.first()
        val isOnline = !forceOffline && connectivityObserver.hasInternet() && !apiKey.isNullOrBlank()

        return@withContext if (isOnline) {
            runOnline(bitmap, category, imagePath, apiKey!!)
        } else {
            runOffline(bitmap, category, imagePath)
        }
    }

    private suspend fun runOnline(
        bitmap: Bitmap,
        category: ScanCategory,
        imagePath: String,
        apiKey: String
    ): AnalysisOutcome {
        val client = GeminiClient(apiKey)
        val result = client.analyze(bitmap, category, LanguageManager.getCurrentLanguage().code)

        return result.fold(
            onSuccess = { geminiResult ->
                AnalysisOutcome.Success(
                    ScanResult(
                        category = category,
                        imagePath = imagePath,
                        predictedLabel = geminiResult.predictedLabel,
                        confidence = geminiResult.confidence,
                        allScores = mapOf(geminiResult.predictedLabel to geminiResult.confidence),
                        conditionInfo = geminiResult.conditionInfo,
                        source = AnalysisSource.ONLINE_AI,
                        skinTone = SkinToneAnalyzer.analyze(bitmap)
                    )
                )
            },
            onFailure = {
                // Graceful fallback: if the online call fails (bad key, quota, network hiccup),
                // fall back to the offline model rather than showing an error.
                runOffline(bitmap, category, imagePath)
            }
        )
    }

    private fun runOffline(
        bitmap: Bitmap,
        category: ScanCategory,
        imagePath: String
    ): AnalysisOutcome {
        return try {
            val classifier = getClassifier(category)
            val scores = classifier.classify(bitmap)

            val maxIndex = scores.indices.maxByOrNull { scores[it] } ?: 0
            val predictedLabel = category.labels.getOrElse(maxIndex) { category.labels.first() }
            val confidence = scores.getOrElse(maxIndex) { 0f }

            val allScores = category.labels.zip(scores.toList()).toMap()
            val conditionInfo = OfflineContentRepository.get(category, predictedLabel)

            AnalysisOutcome.Success(
                ScanResult(
                    category = category,
                    imagePath = imagePath,
                    predictedLabel = predictedLabel,
                    confidence = confidence,
                    allScores = allScores,
                    conditionInfo = conditionInfo,
                    source = AnalysisSource.OFFLINE_MODEL,
                    skinTone = SkinToneAnalyzer.analyze(bitmap)
                )
            )
        } catch (e: Exception) {
            AnalysisOutcome.Error("Analysis failed: ${e.message}")
        }
    }

    fun release() {
        classifierCache.values.forEach { it.close() }
        classifierCache.clear()
    }
}
