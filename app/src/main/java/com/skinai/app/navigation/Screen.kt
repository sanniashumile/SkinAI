package com.skinai.app.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object Scan : Screen("scan/{categoryName}") {
        fun createRoute(categoryName: String) = "scan/$categoryName"
    }
    data object Result : Screen("result")
    data object History : Screen("history")
    data object Glossary : Screen("glossary")
    data object GlossaryDetail : Screen("glossary_detail/{category}/{label}") {
        fun createRoute(category: String, label: String) = "glossary_detail/$category/$label"
    }
    data object Settings : Screen("settings")
    data object Profile : Screen("profile")
    data object ContactDermatologist : Screen("contact_dermatologist")
    data object HowItWorks : Screen("how_it_works")
}
