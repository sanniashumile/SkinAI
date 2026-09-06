package com.skinai.app.data.model

enum class UrgencyLevel(val label: String, val colorHex: Long) {
    LOW("Low Concern", 0xFF4CAF50),
    MONITOR("Monitor", 0xFFFFC107),
    SEE_SOON("See a Doctor Soon", 0xFFFF9800),
    URGENT("Urgent - See a Doctor", 0xFFF44336)
}

/**
 * Structured recommendation content shown on the Results screen and Glossary screen.
 * Used both for offline (bundled) content and to shape the schema requested from Gemini online.
 */
data class ConditionInfo(
    val label: String,             // raw model label
    val displayName: String,       // human-friendly name
    val category: ScanCategory,
    val overview: String,
    val symptoms: List<String>,
    val warningSigns: List<String>,
    val precautions: List<String>,
    val careTips: List<String>,
    val lifestyleAdvice: List<String>,
    val whenToSeeDoctor: String,
    val urgency: UrgencyLevel
)

/**
 * A completed scan result, combining the model prediction with its recommendation content.
 */
data class ScanResult(
    val id: Long = 0,
    val category: ScanCategory,
    val imagePath: String,
    val predictedLabel: String,
    val confidence: Float,
    val allScores: Map<String, Float>,
    val conditionInfo: ConditionInfo,
    val source: AnalysisSource,
    val skinTone: FitzpatrickType,
    val timestamp: Long = System.currentTimeMillis()
)

enum class AnalysisSource { ONLINE_AI, OFFLINE_MODEL }
