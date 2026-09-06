package com.skinai.app.data.offline

import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.UrgencyLevel

/** Offline bundled recommendation content for the Mole/Spot Check model's 8 classes. */
object MoleCheckContent {
    val items: Map<String, ConditionInfo> = listOf(
        ConditionInfo(
            label = "Actinic keratosis",
            displayName = "Actinic Keratosis",
            category = ScanCategory.MOLE_CHECK,
            overview = "A rough, scaly patch caused by years of sun exposure. Considered precancerous — it can occasionally progress to skin cancer if left untreated.",
            symptoms = listOf("Rough, dry, scaly patch", "Pink, red, or skin-toned color", "Often on sun-exposed areas (face, ears, hands, scalp)"),
            warningSigns = listOf("Patch grows, thickens, or becomes tender", "Bleeding or crusting that doesn't heal", "Rapid change in size or texture"),
            precautions = listOf("Never pick or scratch the patch", "Always wear sunscreen, even on cloudy days", "Avoid tanning beds"),
            careTips = listOf("Use broad-spectrum SPF 30+ daily", "Wear a wide-brimmed hat and protective clothing outdoors", "Avoid peak sun hours (10am–4pm)"),
            lifestyleAdvice = listOf("Get skin checked regularly if you have a history of sun exposure"),
            whenToSeeDoctor = "This finding should be evaluated by a dermatologist — actinic keratosis is generally treatable but needs a professional exam to confirm and treat properly.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Basal cell carcinoma",
            displayName = "Basal Cell Carcinoma",
            category = ScanCategory.MOLE_CHECK,
            overview = "The most common form of skin cancer. It grows slowly and rarely spreads to other parts of the body, but it can damage surrounding tissue if untreated.",
            symptoms = listOf("Pearly or waxy bump", "Flat, flesh-colored or brown scar-like lesion", "Sore that bleeds, oozes, or crusts and doesn't heal"),
            warningSigns = listOf("Sore that doesn't heal within a few weeks", "Visible blood vessels on the bump", "Central indentation with raised, rolled edges"),
            precautions = listOf("Avoid unprotected sun exposure", "Don't ignore a 'pimple' that never fully heals", "Avoid tanning beds"),
            careTips = listOf("Apply SPF 30+ sunscreen daily", "Cover the area if outdoors for long periods", "Keep the area clean and avoid irritation"),
            lifestyleAdvice = listOf("Schedule annual full-body skin checks, especially with prior sun damage"),
            whenToSeeDoctor = "See a dermatologist promptly. Basal cell carcinoma is highly treatable when caught early, but delaying care allows it to grow and damage surrounding skin.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Benign keratosis",
            displayName = "Benign Keratosis",
            category = ScanCategory.MOLE_CHECK,
            overview = "A common, non-cancerous skin growth (such as a seborrheic keratosis). Usually harmless but can sometimes resemble more serious lesions.",
            symptoms = listOf("Waxy, 'stuck-on' appearance", "Tan, brown, or black coloring", "Slightly raised, rough surface"),
            warningSigns = listOf("Rapid change in size, shape, or color", "Bleeding without injury", "New irregular border developing"),
            precautions = listOf("Avoid picking or scratching the growth", "Don't attempt to remove it at home"),
            careTips = listOf("Moisturize surrounding skin to reduce irritation", "Protect the area from sun exposure"),
            lifestyleAdvice = listOf("Track the growth with photos over time to catch changes early"),
            whenToSeeDoctor = "Usually harmless and doesn't require urgent care, but have any new or changing growth checked by a dermatologist to confirm it isn't something else.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Dermatofibroma",
            displayName = "Dermatofibroma",
            category = ScanCategory.MOLE_CHECK,
            overview = "A common, benign, firm bump that often forms after a minor skin injury like a bug bite. Harmless and typically doesn't require treatment.",
            symptoms = listOf("Small, firm, raised bump", "Brown, pink, or reddish color", "Dimples inward when pinched from the sides"),
            warningSigns = listOf("Rapid growth", "Ulceration or bleeding", "Significant pain or tenderness"),
            precautions = listOf("Avoid repeated trauma to the area (shaving, scratching)"),
            careTips = listOf("Leave it alone if not bothersome", "Moisturize if the area feels dry or itchy"),
            lifestyleAdvice = listOf("No special lifestyle changes needed for typical cases"),
            whenToSeeDoctor = "Generally not urgent. See a dermatologist if it grows quickly, becomes painful, or you'd like it removed for cosmetic reasons.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Melanocytic nevus",
            displayName = "Melanocytic Nevus (Common Mole)",
            category = ScanCategory.MOLE_CHECK,
            overview = "A common mole formed by a cluster of pigment-producing cells. The vast majority are completely benign and stay stable for years.",
            symptoms = listOf("Round or oval shape", "Even color (tan, brown, or black)", "Smooth, well-defined border"),
            warningSigns = listOf("Asymmetry — one half doesn't match the other", "Border irregularity — ragged or blurred edges", "Color variation within the same mole", "Diameter larger than a pencil eraser (6mm)", "Evolving — any change in size, shape, or color (the ABCDE rule)"),
            precautions = listOf("Protect moles from sun exposure", "Avoid picking, shaving over, or irritating moles"),
            careTips = listOf("Perform monthly self skin-checks", "Photograph moles to track changes over time"),
            lifestyleAdvice = listOf("Use sunscreen daily to reduce risk of new abnormal moles forming"),
            whenToSeeDoctor = "Routine moles don't need urgent evaluation, but see a dermatologist if you notice any ABCDE warning signs above.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Melanoma",
            displayName = "Melanoma",
            category = ScanCategory.MOLE_CHECK,
            overview = "The most serious form of skin cancer. It can spread to other organs if not caught early, but is highly treatable when detected in its early stages.",
            symptoms = listOf("Asymmetrical mole or new dark spot", "Irregular, notched, or blurred border", "Multiple colors within one lesion", "Diameter greater than 6mm", "Mole that is changing, itching, or bleeding"),
            warningSigns = listOf("Any of the ABCDE signs present", "New pigmented lesion appearing after age 30", "A mole that looks different from all your other moles ('ugly duckling' sign)", "Bleeding, itching, or crusting"),
            precautions = listOf("Do not delay seeking evaluation", "Avoid sun exposure and tanning beds entirely on the affected area", "Do not attempt any home remedy or removal"),
            careTips = listOf("Protect the area from further sun exposure until evaluated"),
            lifestyleAdvice = listOf("Schedule regular dermatologist visits especially with a personal or family history of melanoma"),
            whenToSeeDoctor = "Please see a dermatologist as soon as possible. This result is flagged for urgent evaluation — early detection significantly improves treatment outcomes.",
            urgency = UrgencyLevel.URGENT
        ),
        ConditionInfo(
            label = "Squamous cell carcinoma",
            displayName = "Squamous Cell Carcinoma",
            category = ScanCategory.MOLE_CHECK,
            overview = "The second most common form of skin cancer. It can grow deeper into skin layers and, if untreated, has a higher chance than basal cell carcinoma of spreading.",
            symptoms = listOf("Firm, red nodule", "Flat lesion with a scaly, crusted surface", "Sore that doesn't heal or reopens repeatedly"),
            warningSigns = listOf("Rapid growth over weeks", "Pain, tenderness, or bleeding", "Ulceration that won't heal"),
            precautions = listOf("Avoid further sun exposure to the area", "Don't delay evaluation while monitoring it yourself"),
            careTips = listOf("Keep the area covered and protected from the sun", "Avoid irritating the site with tight clothing"),
            lifestyleAdvice = listOf("Get routine skin checks, especially with high cumulative sun exposure"),
            whenToSeeDoctor = "See a dermatologist promptly. Squamous cell carcinoma is very treatable when caught early, but timely diagnosis matters.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Vascular lesion",
            displayName = "Vascular Lesion",
            category = ScanCategory.MOLE_CHECK,
            overview = "A growth made up of blood vessels, such as a cherry angioma or hemangioma. The overwhelming majority are completely benign.",
            symptoms = listOf("Red, purple, or blue coloring", "Small, dome-shaped or flat", "May blanch (lighten) briefly when pressed"),
            warningSigns = listOf("Rapid growth", "Bleeding without clear injury", "New irregular shape or dark coloring within it"),
            precautions = listOf("Avoid scratching or injuring the lesion, as vascular spots can bleed more than typical skin"),
            careTips = listOf("No special care usually needed for stable spots"),
            lifestyleAdvice = listOf("These often increase in number with age and are typically nothing to worry about"),
            whenToSeeDoctor = "Usually not urgent. See a doctor if it changes rapidly, bleeds easily, or looks different from other vascular spots you have.",
            urgency = UrgencyLevel.LOW
        )
    ).associateBy { it.label }
}
