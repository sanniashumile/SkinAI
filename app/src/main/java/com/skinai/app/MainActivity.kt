package com.skinai.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.skinai.app.navigation.SkinAINavHost
import com.skinai.app.ui.theme.AppThemeMode
import com.skinai.app.ui.theme.SkinAITheme

class MainActivity : AppCompatActivity() {

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SkinAIApp(viewModel)
        }
    }
}

@Composable
fun SkinAIApp(viewModel: AppViewModel) {
    val themeModeString by viewModel.themeModeString.collectAsState()
    val themeMode = when (themeModeString) {
        "light" -> AppThemeMode.LIGHT
        "dark" -> AppThemeMode.DARK
        else -> AppThemeMode.SYSTEM
    }
    SkinAITheme(themeMode = themeMode) {
        Surface(modifier = Modifier.fillMaxSize()) {
            SkinAINavHost(viewModel = viewModel)
        }
    }
}
