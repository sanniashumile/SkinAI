package com.skinai.app.data.offline

import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.ScanCategory

/**
 * Single access point for all bundled offline recommendation content.
 * Used for offline-mode analysis results AND for the always-offline Glossary screen.
 */
object OfflineContentRepository {

    private val byCategory: Map<ScanCategory, Map<String, ConditionInfo>> = mapOf(
        ScanCategory.MOLE_CHECK to MoleCheckContent.items,
        ScanCategory.GENERAL_CHECK to GeneralCheckContent.items,
        ScanCategory.COSMETIC_CHECK to CosmeticCheckContent.items
    )

    fun get(category: ScanCategory, label: String): ConditionInfo {
        return byCategory[category]?.get(label)
            ?: fallback(category, label)
    }

    /** All entries for a category, in the model's original label order — used by the Glossary screen. */
    fun allForCategory(category: ScanCategory): List<ConditionInfo> {
        val map = byCategory[category] ?: return emptyList()
        return category.labels.mapNotNull { map[it] }
    }

    /** Every entry across all 3 categories, for the full glossary list/search. */
    fun allEntries(): List<ConditionInfo> {
        return ScanCategory.entries.flatMap { allForCategory(it) }
    }

    private fun fallback(category: ScanCategory, label: String) = ConditionInfo(
        label = label,
        displayName = label,
        category = category,
        overview = "No detailed offline information is available for this result yet.",
        symptoms = emptyList(),
        warningSigns = emptyList(),
        precautions = listOf("When in doubt, have any unusual or changing skin finding checked by a professional."),
        careTips = emptyList(),
        lifestyleAdvice = emptyList(),
        whenToSeeDoctor = "Please consult a dermatologist for a proper evaluation of this finding.",
        urgency = com.skinai.app.data.model.UrgencyLevel.MONITOR
    )
}
