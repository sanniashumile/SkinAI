package com.skinai.app.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skinai.app.AnalysisUiState
import com.skinai.app.AppViewModel
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.offline.OfflineContentRepository
import com.skinai.app.ui.screens.*

@Composable
fun SkinAINavHost(viewModel: AppViewModel) {
    val navController = rememberNavController()
    val analysisState by viewModel.analysisState.collectAsState()
    val history by viewModel.history.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        enterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(220)) + slideInHorizontally(initialOffsetX = { it / 6 }) },
        exitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(180)) },
        popEnterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(220)) },
        popExitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(180)) + slideOutHorizontally(targetOffsetX = { it / 6 }) }
    ) {

        composable(Screen.Onboarding.route) {
            val onboardingComplete by viewModel.onboardingComplete.collectAsState()

            androidx.compose.runtime.LaunchedEffect(onboardingComplete) {
                if (onboardingComplete) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            }

            OnboardingScreen(
                onFinished = {
                    viewModel.completeOnboarding()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onCategorySelected = { category ->
                    viewModel.resetAnalysisState()
                    navController.navigate(Screen.Scan.createRoute(category.name))
                },
                onHistoryClick = { navController.navigate(Screen.History.route) },
                onGlossaryClick = { navController.navigate(Screen.Glossary.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onContactDermatologistClick = { navController.navigate(Screen.ContactDermatologist.route) },
                onHowItWorksClick = { navController.navigate(Screen.HowItWorks.route) }
            )
        }

        composable(Screen.Profile.route) {
            val name by viewModel.userName.collectAsState()
            val dob by viewModel.userDob.collectAsState()
            val cnic by viewModel.userCnic.collectAsState()
            ProfileScreen(
                viewModel = viewModel,
                currentName = name,
                currentDob = dob,
                currentCnic = cnic,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ContactDermatologist.route) {
            ContactDermatologistScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.HowItWorks.route) {
            HowItWorksScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.Scan.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ScanCategory.GENERAL_CHECK.name
            val category = ScanCategory.valueOf(categoryName)

            ScanScreen(
                category = category,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onAnalysisComplete = { navController.navigate(Screen.Result.route) }
            )
        }

        composable(Screen.Result.route) {
            val state = analysisState
            if (state is AnalysisUiState.Success) {
                ResultScreen(
                    result = state.result,
                    onBack = { navController.popBackStack(Screen.Home.route, inclusive = false) },
                    onScanAgain = {
                        viewModel.resetAnalysisState()
                        navController.popBackStack(Screen.Home.route, inclusive = false)
                    },
                    onContactDermatologist = { navController.navigate(Screen.ContactDermatologist.route) }
                )
            }
        }

        composable(Screen.History.route) {
            HistoryScreen(
                history = history,
                onBack = { navController.popBackStack() },
                onResultClick = { /* could navigate to a read-only result detail */ }
            )
        }

        composable(Screen.Glossary.route) {
            GlossaryScreen(
                onBack = { navController.popBackStack() },
                onEntryClick = { entry ->
                    navController.navigate(Screen.GlossaryDetail.createRoute(entry.category.name, entry.label))
                }
            )
        }

        composable(
            route = Screen.GlossaryDetail.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("label") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("category") ?: ScanCategory.GENERAL_CHECK.name
            val label = backStackEntry.arguments?.getString("label") ?: ""
            val category = ScanCategory.valueOf(categoryName)
            val info = OfflineContentRepository.get(category, label)

            GlossaryDetailScreen(
                info = info,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
