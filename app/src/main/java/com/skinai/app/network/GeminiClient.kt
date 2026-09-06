package com.skinai.app.network

import android.graphics.Bitmap
import android.util.Base64
import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.UrgencyLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

/**
 * Calls Google's Gemini API (AI Studio) for online skin analysis + AI-generated
 * structured recommendations. Requires a user-supplied API key (entered in Settings).
 */
class GeminiClient(private val apiKey: String) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        // Free-tier friendly, vision-capable Gemini model
        private const val MODEL = "gemini-2.0-flash"
        private const val ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/$MODEL:generateContent"
    }

    data class GeminiResult(
        val predictedLabel: String,
        val confidence: Float,
        val conditionInfo: ConditionInfo
    )

    suspend fun analyze(bitmap: Bitmap, category: ScanCategory, languageCode: String = "en"): Result<GeminiResult> =
        withContext(Dispatchers.IO) {
            try {
                val base64Image = bitmapToBase64(bitmap)
                val prompt = buildPrompt(category, languageCode)

                val requestBodyJson = buildJsonObject {
                    putJsonArray("contents") {
                        addJsonObject {
                            putJsonArray("parts") {
                                addJsonObject { put("text", prompt) }
                                addJsonObject {
                                    putJsonObject("inline_data") {
                                        put("mime_type", "image/jpeg")
                                        put("data", base64Image)
                                    }
                                }
                            }
                        }
                    }
                    putJsonObject("generationConfig") {
                        put("temperature", 0.2)
                        put("response_mime_type", "application/json")
                    }
                }

                val request = Request.Builder()
                    .url("$ENDPOINT?key=$apiKey")
                    .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        return@withContext Result.failure(
                            Exception("Gemini API error: ${response.code} ${response.message}")
                        )
                    }
                    val bodyStr = response.body?.string()
                        ?: return@withContext Result.failure(Exception("Empty response from Gemini"))

                    val parsed = parseGeminiResponse(bodyStr, category)
                    Result.success(parsed)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private fun buildPrompt(category: ScanCategory, languageCode: String): String {
        val labelList = category.labels.joinToString(", ")
        val languageInstruction = if (languageCode == "ur") {
            "Write ALL text field values (overview, symptoms, warningSigns, precautions, careTips, lifestyleAdvice, whenToSeeDoctor) in Urdu (اردو), using natural, clear Urdu suitable for a general audience. Keep \"predictedLabel\" and \"urgency\" in English exactly as given in the lists below, since the app matches those programmatically."
        } else {
            "Write all text fields in clear, simple English suitable for a general audience."
        }
        return """
            You are a dermatology screening assistant analyzing a skin photo for the "${category.displayName}" category.
            Choose the single most likely label ONLY from this exact list: [$labelList]

            $languageInstruction

            Respond with ONLY valid JSON (no markdown, no extra text) in exactly this schema:
            {
              "predictedLabel": "<one label exactly matching the list above>",
              "confidence": <number 0.0-1.0>,
              "overview": "<2-3 sentence plain-language explanation>",
              "symptoms": ["<symptom 1>", "<symptom 2>", "..."],
              "warningSigns": ["<warning sign 1>", "..."],
              "precautions": ["<precaution 1>", "..."],
              "careTips": ["<care tip 1>", "..."],
              "lifestyleAdvice": ["<advice 1>", "..."],
              "whenToSeeDoctor": "<1-2 sentence guidance on urgency and next steps>",
              "urgency": "<one of: LOW, MONITOR, SEE_SOON, URGENT>"
            }

            Important: This is for general educational/informational purposes only, not a medical diagnosis.
            Be conservative — if signs are ambiguous or could indicate something serious, lean toward a higher urgency level.
        """.trimIndent()
    }

    private fun parseGeminiResponse(bodyStr: String, category: ScanCategory): GeminiResult {
        val root = json.parseToJsonElement(bodyStr).jsonObject
        val text = root["candidates"]!!.jsonArray[0].jsonObject["content"]!!.jsonObject["parts"]!!
            .jsonArray[0].jsonObject["text"]!!.jsonPrimitive.content

        val resultJson = json.parseToJsonElement(text).jsonObject

        val label = resultJson["predictedLabel"]?.jsonPrimitive?.content ?: category.labels.first()
        val confidence = resultJson["confidence"]?.jsonPrimitive?.doubleOrNull?.toFloat() ?: 0.5f
        val urgencyStr = resultJson["urgency"]?.jsonPrimitive?.content ?: "MONITOR"
        val urgency = try {
            UrgencyLevel.valueOf(urgencyStr)
        } catch (e: Exception) {
            UrgencyLevel.MONITOR
        }

        fun stringList(key: String): List<String> =
            resultJson[key]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList()

        val conditionInfo = ConditionInfo(
            label = label,
            displayName = label,
            category = category,
            overview = resultJson["overview"]?.jsonPrimitive?.content ?: "",
            symptoms = stringList("symptoms"),
            warningSigns = stringList("warningSigns"),
            precautions = stringList("precautions"),
            careTips = stringList("careTips"),
            lifestyleAdvice = stringList("lifestyleAdvice"),
            whenToSeeDoctor = resultJson["whenToSeeDoctor"]?.jsonPrimitive?.content ?: "",
            urgency = urgency
        )

        return GeminiResult(label, confidence, conditionInfo)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }
}
