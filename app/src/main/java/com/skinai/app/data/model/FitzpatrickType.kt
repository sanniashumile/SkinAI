package com.skinai.app.data.model

/**
 * The Fitzpatrick Skin Type scale (I-VI), a dermatology-standard classification
 * of skin's sun sensitivity and reaction to UV exposure.
 */
enum class FitzpatrickType(
    val romanNumeral: String,
    val label: String,
    val description: String,
    val sunReaction: String,
    val recommendedSpf: String
) {
    TYPE_I(
        "I", "Very Light",
        "Very fair skin, often with light eyes and hair.",
        "Always burns easily, rarely tans. Highest sun sensitivity.",
        "SPF 50+, reapplied every 2 hours outdoors"
    ),
    TYPE_II(
        "II", "Light",
        "Fair skin, often with light eyes.",
        "Usually burns easily, tans minimally.",
        "SPF 50+, reapplied every 2 hours outdoors"
    ),
    TYPE_III(
        "III", "Medium",
        "Fair to medium skin tone.",
        "Sometimes burns, gradually tans to light brown.",
        "SPF 30-50, reapplied every 2-3 hours outdoors"
    ),
    TYPE_IV(
        "IV", "Olive",
        "Olive or light brown skin tone.",
        "Rarely burns, tans easily to moderate brown.",
        "SPF 30+, reapplied every 3 hours outdoors"
    ),
    TYPE_V(
        "V", "Brown",
        "Brown skin tone.",
        "Very rarely burns, tans deeply and easily.",
        "SPF 30, reapplied when outdoors for extended periods"
    ),
    TYPE_VI(
        "VI", "Dark Brown / Black",
        "Deeply pigmented dark brown to black skin tone.",
        "Almost never burns, deeply pigmented naturally.",
        "SPF 30, still recommended for daily protection"
    );

    val sunCareTips: List<String>
        get() = when (this) {
            TYPE_I, TYPE_II -> listOf(
                "Seek shade during peak sun hours (10am-4pm)",
                "Wear sun-protective clothing and a wide-brimmed hat",
                "Schedule annual skin checks — this skin type has the highest skin cancer risk"
            )
            TYPE_III, TYPE_IV -> listOf(
                "Apply sunscreen daily even on cloudy days",
                "Wear protective clothing during prolonged sun exposure",
                "Get periodic skin checks, especially with a history of sunburns"
            )
            TYPE_V, TYPE_VI -> listOf(
                "Daily sunscreen still matters — UV can cause hyperpigmentation and damage",
                "Skin cancer is less common but often diagnosed later — don't skip skin checks",
                "Moisturize regularly, as deeper skin tones can show dryness differently"
            )
        }
}
