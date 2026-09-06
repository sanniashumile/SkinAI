package com.skinai.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "skinai_settings")

/**
 * Stores the user's manually-entered Gemini API key and app preferences locally on-device.
 * The key is never bundled with the app or sent anywhere except directly to Google's API.
 */
class SettingsStore(private val context: Context) {

    private object Keys {
        val GEMINI_API_KEY = stringPreferencesKey("gemini_api_key")
        val THEME_MODE = stringPreferencesKey("theme_mode") // "light" | "dark" | "system"
        val FORCE_OFFLINE = booleanPreferencesKey("force_offline")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_DOB = stringPreferencesKey("user_dob")
        val USER_CNIC = stringPreferencesKey("user_cnic")
        val ONBOARDING_COMPLETE = booleanPreferencesKey("onboarding_complete")
    }

    val onboardingComplete: Flow<Boolean> = context.dataStore.data.map { it[Keys.ONBOARDING_COMPLETE] ?: false }

    suspend fun setOnboardingComplete() {
        context.dataStore.edit { it[Keys.ONBOARDING_COMPLETE] = true }
    }

    val apiKey: Flow<String?> = context.dataStore.data.map { it[Keys.GEMINI_API_KEY] }
    val themeMode: Flow<String> = context.dataStore.data.map { it[Keys.THEME_MODE] ?: "system" }
    val forceOffline: Flow<Boolean> = context.dataStore.data.map { it[Keys.FORCE_OFFLINE] ?: false }
    val userName: Flow<String?> = context.dataStore.data.map { it[Keys.USER_NAME] }
    val userDob: Flow<String?> = context.dataStore.data.map { it[Keys.USER_DOB] }
    val userCnic: Flow<String?> = context.dataStore.data.map { it[Keys.USER_CNIC] }

    suspend fun setApiKey(key: String) {
        context.dataStore.edit { it[Keys.GEMINI_API_KEY] = key }
    }

    suspend fun clearApiKey() {
        context.dataStore.edit { it.remove(Keys.GEMINI_API_KEY) }
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode }
    }

    suspend fun setForceOffline(value: Boolean) {
        context.dataStore.edit { it[Keys.FORCE_OFFLINE] = value }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { it[Keys.USER_NAME] = name }
    }

    suspend fun setUserProfile(name: String, dob: String, cnic: String) {
        context.dataStore.edit {
            it[Keys.USER_NAME] = name
            it[Keys.USER_DOB] = dob
            it[Keys.USER_CNIC] = cnic
        }
    }
}
