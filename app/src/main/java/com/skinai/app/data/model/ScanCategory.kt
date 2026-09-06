package com.skinai.app.data.model

/**
 * The 3 scan categories, each backed by its own on-device TFLite model.
 */
enum class ScanCategory(
    val displayName: String,
    val subtitle: String,
    val modelAsset: String,
    val inputSize: Int,
    val outputIsSoftmax: Boolean, // true if model already applies softmax internally
    val labels: List<String>
) {
    MOLE_CHECK(
        displayName = "Check a Spot / Mole",
        subtitle = "Focused screening for moles and lesions",
        modelAsset = "model_mole_check.tflite",
        inputSize = 380,
        outputIsSoftmax = false,
        labels = listOf(
            "Actinic keratosis",
            "Basal cell carcinoma",
            "Benign keratosis",
            "Dermatofibroma",
            "Melanocytic nevus",
            "Melanoma",
            "Squamous cell carcinoma",
            "Vascular lesion"
        )
    ),
    GENERAL_CHECK(
        displayName = "General Skin Check",
        subtitle = "Broad screening for common skin conditions",
        modelAsset = "model_general_check.tflite",
        inputSize = 224,
        outputIsSoftmax = false,
        labels = listOf(
            "Acne",
            "Actinic_Keratosis",
            "Benign_tumors",
            "Bullous",
            "Candidiasis",
            "DrugEruption",
            "Eczema",
            "Infestations_Bites",
            "Lichen",
            "Lupus",
            "Moles",
            "Psoriasis",
            "Rosacea",
            "Seborrh_Keratoses",
            "SkinCancer",
            "Sun_Sunlight_Damage",
            "Tinea",
            "Unknown_Normal",
            "Vascular_Tumors",
            "Vasculitis",
            "Vitiligo",
            "Warts"
        )
    ),
    COSMETIC_CHECK(
        displayName = "Cosmetic Checkup",
        subtitle = "Skin texture & appearance analysis",
        modelAsset = "model_cosmetic_check.tflite",
        inputSize = 224,
        outputIsSoftmax = true,
        labels = listOf(
            "acne",
            "blackheades",
            "dark spots",
            "pores",
            "wrinkles"
        )
    )
}
