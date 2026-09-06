package com.skinai.app

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skinai.app.data.model.AnalysisSource
import com.skinai.app.data.model.ScanCategory
import com.skinai.app.data.model.ScanResult
import com.skinai.app.data.repository.AnalysisOutcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AnalysisUiState {
    data object Idle : AnalysisUiState()
    data object Loading : AnalysisUiState()
    data class Success(val result: ScanResult) : AnalysisUiState()
    data class Error(val message: String) : AnalysisUiState()
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val app get() = getApplication<SkinAIApplication>()

    private val _analysisState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Idle)
    val analysisState: StateFlow<AnalysisUiState> = _analysisState.asStateFlow()

    // In-memory history for this session (persisted history can be added via Room later)
    private val _history = MutableStateFlow<List<ScanResult>>(emptyList())
    val history: StateFlow<List<ScanResult>> = _history.asStateFlow()

    val isOnline: StateFlow<Boolean> = app.connectivityObserver.isOnline
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, app.connectivityObserver.hasInternet())

    val apiKey: StateFlow<String?> = app.settingsStore.apiKey
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, null)

    val forceOffline: StateFlow<Boolean> = app.settingsStore.forceOffline
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, false)

    val themeModeString: StateFlow<String> = app.settingsStore.themeMode
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, "system")

    val userName: StateFlow<String?> = app.settingsStore.userName
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, null)
    val userDob: StateFlow<String?> = app.settingsStore.userDob
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, null)
    val userCnic: StateFlow<String?> = app.settingsStore.userCnic
        .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, null)

    // In-memory only for now — resets every fresh app launch (not persisted to disk).
    // This guarantees onboarding is always shown on a new process start, avoiding any
    // confusion from stale "completed" state surviving across debug reinstalls.
    private val _onboardingComplete = MutableStateFlow(false)
    val onboardingComplete: StateFlow<Boolean> = _onboardingComplete.asStateFlow()

    fun completeOnboarding() {
        _onboardingComplete.value = true
    }

    fun setThemeMode(mode: String) = viewModelScope.launch {
        app.settingsStore.setThemeMode(mode)
    }

    fun saveUserProfile(name: String, dob: String, cnic: String) = viewModelScope.launch {
        app.settingsStore.setUserProfile(name, dob, cnic)
    }

    fun analyze(bitmap: Bitmap, category: ScanCategory, imagePath: String) {
        _analysisState.value = AnalysisUiState.Loading
        viewModelScope.launch {
            when (val outcome = app.analysisRepository.analyze(bitmap, category, imagePath)) {
                is AnalysisOutcome.Success -> {
                    _analysisState.value = AnalysisUiState.Success(outcome.result)
                    _history.value = listOf(outcome.result) + _history.value
                }
                is AnalysisOutcome.Error -> {
                    _analysisState.value = AnalysisUiState.Error(outcome.message)
                }
            }
        }
    }

    fun resetAnalysisState() {
        _analysisState.value = AnalysisUiState.Idle
    }

    fun saveApiKey(key: String) = viewModelScope.launch {
        app.settingsStore.setApiKey(key)
    }

    fun setForceOffline(value: Boolean) = viewModelScope.launch {
        app.settingsStore.setForceOffline(value)
    }
}
