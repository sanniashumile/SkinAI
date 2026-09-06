package com.skinai.app.data.offline

import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.UrgencyLevel

/** Offline bundled recommendation content for the Cosmetic Checkup model's 5 classes. */
object CosmeticCheckContent {
    private val cat = ScanCategory.COSMETIC_CHECK

    val items: Map<String, ConditionInfo> = listOf(
        ConditionInfo(
            label = "acne", displayName = "Acne", category = cat,
            overview = "Cosmetic-level acne detected — clogged pores producing pimples or blemishes affecting skin appearance.",
            symptoms = listOf("Visible pimples or blemishes", "Uneven skin texture", "Occasional redness around breakouts"),
            warningSigns = listOf("Deep, painful cystic breakouts", "Scarring developing"),
            precautions = listOf("Avoid picking at blemishes to prevent scarring", "Don't over-exfoliate, which can irritate skin further"),
            careTips = listOf("Use a gentle cleanser twice daily", "Try spot treatments with salicylic acid or benzoyl peroxide", "Use oil-free, non-comedogenic makeup and moisturizer"),
            lifestyleAdvice = listOf("Keep phone screens and pillowcases clean — they touch your face often"),
            whenToSeeDoctor = "If breakouts are frequent, painful, or leaving scars, a dermatologist can offer stronger treatment options.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "blackheades", displayName = "Blackheads", category = cat,
            overview = "Small dark bumps caused by clogged pores where the trapped oil and dead skin oxidize and darken at the surface.",
            symptoms = listOf("Small dark or black dots, usually on nose/chin/forehead", "Slightly rough texture in affected areas"),
            warningSigns = listOf("Blackheads becoming inflamed or turning into painful pimples"),
            precautions = listOf("Avoid squeezing blackheads — this can cause scarring or push debris deeper"),
            careTips = listOf("Use salicylic acid or gentle exfoliating cleansers", "Try clay masks weekly to help draw out excess oil", "Avoid heavy, pore-clogging products"),
            lifestyleAdvice = listOf("Double-cleanse at night if you wear makeup or sunscreen during the day"),
            whenToSeeDoctor = "Not medically necessary, but a dermatologist or esthetician can help with persistent or extensive blackheads.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "dark spots", displayName = "Dark Spots (Hyperpigmentation)", category = cat,
            overview = "Patches of skin darker than the surrounding area, often from sun exposure, past acne marks, or hormonal changes.",
            symptoms = listOf("Flat, darker patches of skin", "Common on cheeks, forehead, or areas of past breakouts"),
            warningSigns = listOf("A spot that is raised, irregular, or rapidly changing — this should be checked separately as a possible mole/lesion concern, not just cosmetic pigmentation"),
            precautions = listOf("Always wear sunscreen — sun exposure darkens spots further", "Avoid picking at healing acne, which causes more dark marks"),
            careTips = listOf("Use vitamin C serum or niacinamide to help brighten over time", "Consider ingredients like azelaic acid for stubborn spots", "Be patient — pigmentation fades gradually over months"),
            lifestyleAdvice = listOf("Reapply sunscreen every 2 hours when outdoors to prevent spots from darkening further"),
            whenToSeeDoctor = "Not urgent — this is a cosmetic concern. A dermatologist can recommend prescription-strength options if over-the-counter products aren't enough.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "pores", displayName = "Enlarged Pores", category = cat,
            overview = "Visible, enlarged pores — often related to oil production, sun damage, or natural skin texture and genetics.",
            symptoms = listOf("Visible small openings on skin, especially on the nose and cheeks", "More noticeable with oily skin"),
            warningSigns = emptyList(),
            precautions = listOf("Avoid harsh scrubbing, which can irritate skin and make pores look worse"),
            careTips = listOf("Use a gentle exfoliant like salicylic acid to keep pores clear", "Niacinamide can help minimize the appearance of pores over time", "Use lightweight, non-comedogenic products"),
            lifestyleAdvice = listOf("Consistent skincare matters more than any single product for pore appearance"),
            whenToSeeDoctor = "Purely cosmetic — no medical concern. A dermatologist can suggest professional treatments (like chemical peels) if desired.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "wrinkles", displayName = "Wrinkles / Fine Lines", category = cat,
            overview = "Lines and creases in the skin from natural aging, sun exposure, and repeated facial movements over time.",
            symptoms = listOf("Fine lines around eyes, forehead, or mouth", "Reduced skin elasticity"),
            warningSigns = emptyList(),
            precautions = listOf("Sun exposure accelerates wrinkle formation — daily sunscreen is the most effective prevention"),
            careTips = listOf("Use a retinol or retinoid product to support skin renewal", "Keep skin hydrated with a good moisturizer", "Daily SPF 30+ is the single best anti-aging step"),
            lifestyleAdvice = listOf("Avoid smoking, which accelerates skin aging", "Stay hydrated and maintain a balanced diet"),
            whenToSeeDoctor = "Purely cosmetic. A dermatologist can discuss options like retinoids, peels, or injectables if you'd like a more active approach.",
            urgency = UrgencyLevel.LOW
        )
    ).associateBy { it.label }
}
