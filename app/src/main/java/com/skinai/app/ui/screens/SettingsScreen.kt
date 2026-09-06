package com.skinai.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skinai.app.AppViewModel
import com.skinai.app.R
import com.skinai.app.util.AppLanguage
import com.skinai.app.util.LanguageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: AppViewModel,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            Text(stringResource(R.string.settings_theme_title), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(12.dp))

            val themeModeString by viewModel.themeModeString.collectAsState()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ThemeOption(stringResource(R.string.settings_theme_light), "light", themeModeString, Modifier.weight(1f)) { viewModel.setThemeMode(it) }
                ThemeOption(stringResource(R.string.settings_theme_dark), "dark", themeModeString, Modifier.weight(1f)) { viewModel.setThemeMode(it) }
                ThemeOption(stringResource(R.string.settings_theme_system), "system", themeModeString, Modifier.weight(1f)) { viewModel.setThemeMode(it) }
            }

            Spacer(modifier = Modifier.height(28.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(20.dp))

            Text(stringResource(R.string.settings_language_title), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                stringResource(R.string.settings_language_desc),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            var selectedLanguage by remember { mutableStateOf(LanguageManager.getCurrentLanguage()) }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppLanguage.entries.forEach { language ->
                    FilterChip(
                        selected = selectedLanguage == language,
                        onClick = {
                            selectedLanguage = language
                            LanguageManager.setLanguage(language)
                        },
                        label = { Text(language.displayName) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeOption(
    label: String,
    value: String,
    current: String,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit
) {
    FilterChip(
        selected = current == value,
        onClick = { onSelect(value) },
        label = { Text(label) },
        modifier = modifier
    )
}
