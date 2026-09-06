package com.skinai.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = AccentSage,
    secondary = AccentForest,
    tertiary = AccentAmber,
    background = DeepSpace,
    surface = SurfaceElevated,
    surfaceVariant = SurfaceElevated2,
    onPrimary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor,
    error = UrgencyUrgent
)

private val LightColors = lightColorScheme(
    primary = AccentSage,
    secondary = AccentForest,
    tertiary = AccentAmber,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurface2,
    onPrimary = Color.White,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary,
    outline = Color(0xFFE8D2CA),
    error = UrgencyUrgent
)

enum class AppThemeMode { LIGHT, DARK, SYSTEM }

@Composable
fun SkinAITheme(
    themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        AppThemeMode.LIGHT -> false
        AppThemeMode.DARK -> true
        AppThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
