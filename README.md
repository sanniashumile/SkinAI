# DermAI

AI-powered skin analysis app built with Kotlin and Jetpack Compose. DermAI helps users screen moles, common skin conditions, and cosmetic concerns using on-device machine learning models — no internet connection required for analysis.

## Features

- **Three scan categories**, each backed by its own trained model:
  - **Check a Spot / Mole** — focused screening for moles and lesions (melanoma, basal cell carcinoma, benign nevus, and more)
  - **General Skin Check** — broad screening across common skin conditions (acne, eczema, psoriasis, and more)
  - **Cosmetic Checkup** — skin texture and appearance analysis (pores, wrinkles, dark spots, and more)
- **On-device AI analysis** — all inference runs locally using TensorFlow Lite / LiteRT, so photos never leave the device
- **Fitzpatrick skin tone estimation** — calculated from the photo using the dermatology-standard ITA° (Individual Typology Angle) formula
- **Structured recommendations** for every result — overview, symptoms, warning signs, precautions, care tips, and guidance on when to see a doctor
- **Condition glossary** — browsable reference covering all supported conditions, fully available offline
- **PDF report export** — generate and share a scan summary, useful to bring to a dermatologist visit
- **Multi-language support** — English and Urdu, with full UI localization
- **Light / Dark / System theme**
- **User profile** — name, date of birth, and ID number, stored locally on-device only

## Tech Stack

- **Kotlin** + **Jetpack Compose** (100% Compose UI, no XML layouts)
- **TensorFlow Lite / LiteRT** for on-device model inference
- **Room** for local data persistence
- **DataStore** for user preferences
- **CameraX** for photo capture
- **Material 3** design system

## Disclaimer

This app provides general educational information only. It is **not** a medical diagnosis and is not a substitute for professional medical advice. Always consult a qualified dermatologist for any concerning or persistent skin condition.

## Getting Started

1. Clone this repository
2. Open the project in Android Studio (Kotlin 2.2+, AGP 8.13+)
3. Let Gradle sync complete
4. Build and run on a device or emulator (minimum SDK 26)

## License

This project is currently private/personal. All rights reserved.
