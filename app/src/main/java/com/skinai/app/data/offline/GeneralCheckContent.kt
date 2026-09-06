package com.skinai.app.data.offline

import com.skinai.app.data.model.ConditionInfo
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.UrgencyLevel

/** Offline bundled recommendation content for the General Skin Check model's 22 classes. */
object GeneralCheckContent {
    private val cat = ScanCategory.GENERAL_CHECK

    val items: Map<String, ConditionInfo> = listOf(
        ConditionInfo(
            label = "Acne", displayName = "Acne", category = cat,
            overview = "A common condition where hair follicles become clogged with oil and dead skin cells, causing pimples, blackheads, or cysts.",
            symptoms = listOf("Whiteheads and blackheads", "Red, tender pimples", "Oily skin", "Occasional deep, painful cysts"),
            warningSigns = listOf("Painful, deep cysts that leave scars", "Sudden severe flare-up", "Signs of infection (spreading redness, fever)"),
            precautions = listOf("Avoid picking or popping pimples", "Don't over-wash — this can worsen oil production", "Avoid heavy, pore-clogging products"),
            careTips = listOf("Cleanse twice daily with a gentle cleanser", "Use non-comedogenic moisturizer and sunscreen", "Consider salicylic acid or benzoyl peroxide products"),
            lifestyleAdvice = listOf("Change pillowcases regularly", "Avoid touching your face throughout the day"),
            whenToSeeDoctor = "See a dermatologist if over-the-counter treatments don't help after 6–8 weeks, or if acne is severe/cystic and causing scarring.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Actinic_Keratosis", displayName = "Actinic Keratosis", category = cat,
            overview = "A rough, scaly patch caused by cumulative sun damage. Considered precancerous and worth monitoring closely.",
            symptoms = listOf("Rough, sandpaper-like patch", "Pink, red, or skin-colored", "Common on face, ears, scalp, hands"),
            warningSigns = listOf("Patch thickens or becomes tender", "Bleeding or non-healing crust", "Rapid change in size"),
            precautions = listOf("Avoid sun exposure without protection", "Never pick at the patch"),
            careTips = listOf("Daily broad-spectrum SPF 30+", "Wear protective clothing and a hat outdoors"),
            lifestyleAdvice = listOf("Annual skin checks recommended with a history of sun exposure"),
            whenToSeeDoctor = "Have this evaluated by a dermatologist — treatable, but a professional exam is needed to confirm and manage it.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Benign_tumors", displayName = "Benign Skin Tumor", category = cat,
            overview = "A non-cancerous growth such as a lipoma, skin tag, or cyst. Common and generally harmless.",
            symptoms = listOf("Slow-growing bump", "Soft or firm texture", "Usually painless"),
            warningSigns = listOf("Rapid growth", "New pain or tenderness", "Skin over the bump breaking down or bleeding"),
            precautions = listOf("Avoid squeezing or attempting home removal"),
            careTips = listOf("Leave it alone if it isn't bothering you", "Protect from friction if in a high-contact area"),
            lifestyleAdvice = listOf("Track any changes in size with periodic photos"),
            whenToSeeDoctor = "Not urgent for typical cases. See a doctor if it grows quickly, becomes painful, or you want it removed.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Bullous", displayName = "Bullous Disorder", category = cat,
            overview = "A condition causing fluid-filled blisters on the skin, which can range from minor (friction blisters) to autoimmune blistering diseases.",
            symptoms = listOf("Fluid-filled blisters", "Redness or irritation around blisters", "Itching or burning sensation"),
            warningSigns = listOf("Widespread blistering", "Blisters in the mouth or eyes", "Signs of infection (pus, spreading redness, fever)"),
            precautions = listOf("Don't pop blisters — this raises infection risk", "Avoid tight clothing rubbing on the area"),
            careTips = listOf("Keep the area clean and loosely covered", "Avoid irritating soaps or fabrics"),
            lifestyleAdvice = listOf("Note any new medications started recently — some blistering conditions are drug-related"),
            whenToSeeDoctor = "See a doctor promptly, especially if blisters are widespread, recurring, or involve mucous membranes — some bullous conditions need targeted treatment.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Candidiasis", displayName = "Candidiasis (Yeast Infection)", category = cat,
            overview = "A fungal skin infection caused by an overgrowth of Candida yeast, common in warm, moist areas of the body.",
            symptoms = listOf("Red, itchy rash", "Sometimes with small red bumps or pustules at the edges", "Common in skin folds (underarms, groin, under breasts)"),
            warningSigns = listOf("Rash spreading rapidly", "Signs of infection worsening despite treatment", "Fever alongside skin symptoms"),
            precautions = listOf("Keep affected areas clean and dry", "Avoid tight, non-breathable clothing"),
            careTips = listOf("Use antifungal creams as directed", "Change out of sweaty clothes promptly"),
            lifestyleAdvice = listOf("Manage underlying risk factors like diabetes, which increases yeast infection frequency"),
            whenToSeeDoctor = "See a doctor if the rash doesn't improve with over-the-counter antifungal treatment within 1–2 weeks, or keeps recurring.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "DrugEruption", displayName = "Drug Eruption", category = cat,
            overview = "A skin reaction triggered by a medication, ranging from mild rashes to more serious reactions requiring immediate care.",
            symptoms = listOf("Widespread red rash", "Itching", "Sometimes hives or blistering"),
            warningSigns = listOf("Rash with fever, facial swelling, or difficulty breathing", "Blistering or skin peeling", "Rapid spread across the body"),
            precautions = listOf("Stop the suspected medication only under medical guidance — don't stop on your own without advice for essential medications"),
            careTips = listOf("Keep track of when the rash started relative to any new medications"),
            lifestyleAdvice = listOf("Keep a list of medications to share with your doctor"),
            whenToSeeDoctor = "Seek medical attention promptly, especially if you notice facial swelling, difficulty breathing, fever, or blistering — these can indicate a serious reaction.",
            urgency = UrgencyLevel.URGENT
        ),
        ConditionInfo(
            label = "Eczema", displayName = "Eczema (Atopic Dermatitis)", category = cat,
            overview = "A chronic condition causing dry, itchy, inflamed skin. Often linked to allergies, asthma, or a family history of similar conditions.",
            symptoms = listOf("Dry, scaly patches", "Intense itching", "Redness and inflammation", "Thickened skin from chronic scratching"),
            warningSigns = listOf("Signs of skin infection (oozing, yellow crusting)", "Widespread flare unresponsive to usual treatment"),
            precautions = listOf("Avoid known triggers (harsh soaps, certain fabrics, stress)", "Don't scratch — this worsens inflammation and risks infection"),
            careTips = listOf("Moisturize frequently with fragrance-free creams", "Use lukewarm (not hot) water when bathing", "Consider prescribed topical treatments for flares"),
            lifestyleAdvice = listOf("Identify and avoid personal triggers over time", "Manage stress, which can worsen flares"),
            whenToSeeDoctor = "See a dermatologist if symptoms are persistent, severe, or interfering with sleep/daily life — prescription treatment can help significantly.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Infestations_Bites", displayName = "Infestation / Insect Bites", category = cat,
            overview = "Skin reaction from insect bites or infestations such as scabies or lice, causing itching and irritation.",
            symptoms = listOf("Itchy red bumps", "Sometimes in a line or cluster pattern", "Possible burrow tracks (with scabies)"),
            warningSigns = listOf("Signs of secondary infection from scratching", "Spreading rash affecting household members too", "Intense nighttime itching (possible scabies indicator)"),
            precautions = listOf("Avoid scratching to prevent skin infection", "Wash bedding and clothing in hot water if infestation is suspected"),
            careTips = listOf("Use anti-itch creams or antihistamines for relief", "Cold compresses can ease itching and swelling"),
            lifestyleAdvice = listOf("Check pets and living environment for infestation sources"),
            whenToSeeDoctor = "See a doctor if itching is severe, spreading to others in your household, or not improving — some infestations require prescription treatment.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Lichen", displayName = "Lichen Planus", category = cat,
            overview = "An inflammatory condition causing itchy, flat-topped purplish bumps on the skin, and sometimes inside the mouth.",
            symptoms = listOf("Flat-topped, purplish, itchy bumps", "Fine white lines on the surface (Wickham's striae)", "Occasionally affects mouth or nails"),
            warningSigns = listOf("Painful mouth sores affecting eating", "Rapidly spreading rash", "Nail changes or hair loss in affected areas"),
            precautions = listOf("Avoid scratching to prevent thickened patches (lichenification)"),
            careTips = listOf("Moisturize regularly", "Use prescribed topical steroids as directed for flares"),
            lifestyleAdvice = listOf("Review medications and hepatitis C status with your doctor — both can be linked triggers"),
            whenToSeeDoctor = "See a dermatologist for diagnosis confirmation and treatment, especially if it involves the mouth or is very itchy.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Lupus", displayName = "Lupus (Cutaneous)", category = cat,
            overview = "An autoimmune condition that can cause distinctive skin rashes, often a butterfly-shaped rash across the cheeks and nose, sometimes alongside broader systemic symptoms.",
            symptoms = listOf("Butterfly-shaped facial rash", "Rash worsened by sun exposure", "Discoid (coin-shaped) scaly patches"),
            warningSigns = listOf("Joint pain, fatigue, or fever alongside the rash", "Rash spreading with scarring", "Hair loss accompanying skin symptoms"),
            precautions = listOf("Avoid sun exposure, which can trigger flares", "Don't ignore accompanying joint or fatigue symptoms"),
            careTips = listOf("Use high-SPF sunscreen daily", "Follow prescribed treatment plans closely if diagnosed"),
            lifestyleAdvice = listOf("Manage stress and get adequate rest, as flares can be triggered by both"),
            whenToSeeDoctor = "This pattern should be evaluated by a doctor, ideally a dermatologist or rheumatologist, especially if you have joint pain or fatigue alongside the rash.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Moles", displayName = "Mole (Nevus)", category = cat,
            overview = "A common, usually benign skin growth formed by clustered pigment cells. Most people have several without issue.",
            symptoms = listOf("Round or oval brown/black spot", "Flat or slightly raised", "Stable in size over time"),
            warningSigns = listOf("Asymmetry, irregular border, multiple colors, diameter over 6mm, or evolving appearance (ABCDE rule)"),
            precautions = listOf("Protect moles from excess sun exposure", "Avoid irritating or picking at moles"),
            careTips = listOf("Do monthly self skin-checks", "Photograph moles periodically to track changes"),
            lifestyleAdvice = listOf("Wear sunscreen daily to lower the risk of new abnormal moles"),
            whenToSeeDoctor = "Not urgent for typical moles. See a dermatologist promptly if you notice any ABCDE warning signs.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Psoriasis", displayName = "Psoriasis", category = cat,
            overview = "A chronic autoimmune condition that speeds up skin cell turnover, causing thick, scaly plaques. It has periods of flares and remission.",
            symptoms = listOf("Thick, red patches with silvery scale", "Itching or burning", "Commonly on elbows, knees, scalp, lower back"),
            warningSigns = listOf("Joint pain or swelling (possible psoriatic arthritis)", "Widespread flare covering large body areas", "Signs of skin infection"),
            precautions = listOf("Avoid skin trauma — new plaques can form at injury sites (Koebner phenomenon)", "Limit alcohol, which can worsen flares"),
            careTips = listOf("Moisturize regularly to reduce scaling", "Use prescribed topical treatments consistently", "Consider gentle sun exposure if approved by your doctor"),
            lifestyleAdvice = listOf("Manage stress, a common flare trigger", "Maintain a healthy weight, which can reduce flare severity"),
            whenToSeeDoctor = "See a dermatologist for an ongoing management plan, especially if plaques are widespread or you notice joint symptoms.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Rosacea", displayName = "Rosacea", category = cat,
            overview = "A chronic condition causing facial redness and visible blood vessels, sometimes with small red bumps resembling acne.",
            symptoms = listOf("Persistent facial redness", "Visible small blood vessels", "Sometimes small red bumps or pustules", "Eye irritation in some cases"),
            warningSigns = listOf("Thickening skin, especially around the nose (rhinophyma)", "Persistent eye irritation or vision changes"),
            precautions = listOf("Identify and avoid personal triggers (spicy food, alcohol, heat, sun)", "Avoid harsh skincare products"),
            careTips = listOf("Use gentle, fragrance-free skincare", "Apply daily sunscreen — sun is a common trigger", "Consider prescribed topical or oral treatments for flares"),
            lifestyleAdvice = listOf("Keep a trigger diary to identify personal flare causes"),
            whenToSeeDoctor = "See a dermatologist for a tailored treatment plan, especially if redness is persistent or affecting your eyes.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Seborrh_Keratoses", displayName = "Seborrheic Keratosis", category = cat,
            overview = "An extremely common, benign skin growth that often appears with age. Has a characteristic 'stuck-on', waxy look.",
            symptoms = listOf("Waxy, 'stuck-on' appearance", "Tan, brown, or black coloring", "Slightly raised, rough texture"),
            warningSigns = listOf("Rapid change in size, shape, or color", "Bleeding without injury", "New irregular border"),
            precautions = listOf("Avoid picking or attempting home removal"),
            careTips = listOf("Moisturize surrounding skin if dry or itchy", "Protect from sun exposure"),
            lifestyleAdvice = listOf("Track any new or changing growths with photos"),
            whenToSeeDoctor = "Usually harmless. Have any new or changing growth checked by a dermatologist to confirm it isn't something more serious.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "SkinCancer", displayName = "Possible Skin Cancer", category = cat,
            overview = "This result flags features consistent with a potentially cancerous skin lesion. This requires professional evaluation — it is not a diagnosis.",
            symptoms = listOf("Irregular or asymmetric shape", "Uneven color or multiple colors", "Growing, changing, bleeding, or non-healing sore"),
            warningSigns = listOf("Any of the ABCDE signs (Asymmetry, Border, Color, Diameter, Evolving)", "Bleeding, itching, or crusting without injury", "New growth in an unusual location"),
            precautions = listOf("Do not delay seeking evaluation", "Avoid further sun exposure to the area", "Do not attempt any home treatment or removal"),
            careTips = listOf("Protect the area from the sun until it's evaluated"),
            lifestyleAdvice = listOf("Schedule regular dermatologist visits, especially with risk factors like fair skin or family history"),
            whenToSeeDoctor = "Please see a dermatologist as soon as possible for a proper evaluation. Early diagnosis significantly improves outcomes for skin cancer.",
            urgency = UrgencyLevel.URGENT
        ),
        ConditionInfo(
            label = "Sun_Sunlight_Damage", displayName = "Sun / Sunlight Damage", category = cat,
            overview = "Visible skin changes from cumulative UV exposure over time, including discoloration, texture changes, and premature aging.",
            symptoms = listOf("Uneven pigmentation or dark spots", "Rough texture", "Fine lines and reduced elasticity"),
            warningSigns = listOf("New rough patches that don't resolve", "Spots that grow, bleed, or change — could indicate a precancerous or cancerous change"),
            precautions = listOf("Avoid further unprotected sun exposure", "Avoid tanning beds"),
            careTips = listOf("Use broad-spectrum SPF 30+ daily, reapplied every 2 hours outdoors", "Consider antioxidant serums (like vitamin C) to support skin repair", "Wear protective clothing and a hat"),
            lifestyleAdvice = listOf("Schedule annual skin checks given cumulative sun exposure history"),
            whenToSeeDoctor = "Not urgent for general sun damage, but see a dermatologist if you notice any new, changing, or non-healing spots within the damaged area.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Tinea", displayName = "Tinea (Ringworm / Fungal Infection)", category = cat,
            overview = "A common fungal skin infection, despite the name 'ringworm' it's not caused by a worm. Creates a distinctive ring-shaped rash.",
            symptoms = listOf("Ring-shaped, red, scaly patch", "Clearer skin in the center", "Itching"),
            warningSigns = listOf("Rash spreading rapidly", "No improvement after 2 weeks of antifungal treatment", "Signs of secondary bacterial infection"),
            precautions = listOf("Avoid sharing towels, clothing, or personal items", "Keep affected area clean and dry"),
            careTips = listOf("Use over-the-counter antifungal cream as directed", "Keep skin dry, especially after sweating or bathing"),
            lifestyleAdvice = listOf("Check pets for similar skin issues, as ringworm is often transmitted from animals"),
            whenToSeeDoctor = "See a doctor if the rash doesn't improve with antifungal treatment within 2 weeks, or keeps spreading or recurring.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Unknown_Normal", displayName = "Normal / Healthy Skin", category = cat,
            overview = "No significant abnormality was detected in this image. The skin appears within normal, healthy range.",
            symptoms = emptyList(),
            warningSigns = listOf("If you notice new symptoms later — changes in color, texture, or new growths — rescan or consult a professional"),
            precautions = listOf("Continue sun protection and routine skincare"),
            careTips = listOf("Maintain a simple, consistent skincare routine", "Use daily sunscreen as preventive care"),
            lifestyleAdvice = listOf("Do periodic self skin-checks even when things look normal"),
            whenToSeeDoctor = "No action needed based on this result. Continue routine self-checks and see a dermatologist if you notice any new or unusual changes.",
            urgency = UrgencyLevel.LOW
        ),
        ConditionInfo(
            label = "Vascular_Tumors", displayName = "Vascular Tumor", category = cat,
            overview = "A growth formed from blood vessels, such as a hemangioma. The large majority of vascular tumors are benign.",
            symptoms = listOf("Red, purple, or blue coloring", "Raised or flat lesion", "May be present from birth or develop over time"),
            warningSigns = listOf("Rapid growth", "Bleeding without clear cause", "Ulceration or pain"),
            precautions = listOf("Avoid injuring the area, as vascular lesions can bleed more easily"),
            careTips = listOf("No special care usually needed if stable"),
            lifestyleAdvice = listOf("Monitor for changes with periodic photos"),
            whenToSeeDoctor = "Usually not urgent. See a doctor if it grows rapidly, bleeds, or becomes painful.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Vasculitis", displayName = "Vasculitis", category = cat,
            overview = "Inflammation of blood vessels that can cause a distinctive rash, often small purple/red spots, and may signal an underlying condition needing evaluation.",
            symptoms = listOf("Small purple or red spots (purpura)", "Rash that doesn't fade when pressed", "Sometimes accompanied by fever or joint pain"),
            warningSigns = listOf("Fever, joint pain, or abdominal pain alongside the rash", "Rapidly spreading rash", "Blood in urine or stool"),
            precautions = listOf("Don't dismiss accompanying systemic symptoms like fatigue or fever"),
            careTips = listOf("Keep track of when the rash appeared and any other symptoms"),
            lifestyleAdvice = listOf("Note any recent infections or new medications, which can be triggers"),
            whenToSeeDoctor = "See a doctor promptly, especially with fever, joint pain, or the rash spreading — vasculitis can indicate an underlying condition needing treatment.",
            urgency = UrgencyLevel.SEE_SOON
        ),
        ConditionInfo(
            label = "Vitiligo", displayName = "Vitiligo", category = cat,
            overview = "A condition causing loss of skin pigment, resulting in smooth white patches. It's an autoimmune-related process, not contagious or harmful physically.",
            symptoms = listOf("Smooth, well-defined white patches", "Symmetrical patterns in some cases", "Premature whitening of hair in affected areas"),
            warningSigns = listOf("Rapid spreading of patches", "Patches appearing alongside other autoimmune symptoms"),
            precautions = listOf("Protect depigmented areas from sunburn — they lack natural UV protection"),
            careTips = listOf("Use high-SPF sunscreen on affected patches", "Consider camouflage makeup if desired — purely cosmetic choice"),
            lifestyleAdvice = listOf("Connect with support communities — vitiligo can affect emotional wellbeing and support helps"),
            whenToSeeDoctor = "See a dermatologist to confirm the diagnosis and discuss treatment options, which can help manage or slow progression.",
            urgency = UrgencyLevel.MONITOR
        ),
        ConditionInfo(
            label = "Warts", displayName = "Warts", category = cat,
            overview = "A common, benign skin growth caused by HPV (human papillomavirus). Often resolves on its own but can spread or persist.",
            symptoms = listOf("Rough, raised bump", "Small black dots visible on surface (clotted blood vessels)", "Can appear singly or in clusters"),
            warningSigns = listOf("Rapid spreading to other areas", "Pain interfering with daily activity", "Bleeding or significant changes"),
            precautions = listOf("Avoid picking at warts — this can spread the virus", "Don't share towels, razors, or nail tools"),
            careTips = listOf("Over-the-counter salicylic acid treatments can help", "Keep the area covered to reduce spread"),
            lifestyleAdvice = listOf("Practice good hand hygiene to reduce spread to other skin areas"),
            whenToSeeDoctor = "See a doctor if warts persist despite home treatment, multiply, or appear on the face or genitals, where professional removal is recommended.",
            urgency = UrgencyLevel.LOW
        )
    ).associateBy { it.label }
}
