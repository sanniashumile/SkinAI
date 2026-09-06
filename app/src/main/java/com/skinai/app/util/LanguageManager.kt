package com.skinai.app.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

enum class AppLanguage(val code: String, val displayName: String, val isRtl: Boolean) {
    ENGLISH("en", "English", false),
    URDU("ur", "اردو", true)
}

object LanguageManager {

    /** Applies the chosen language app-wide (persists automatically via AppCompat). */
    fun setLanguage(language: AppLanguage) {
        val localeList = LocaleListCompat.forLanguageTags(language.code)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    fun getCurrentLanguage(): AppLanguage {
        val locales = AppCompatDelegate.getApplicationLocales()
        if (locales.isEmpty) return AppLanguage.ENGLISH
        val tag = locales[0]?.language
        return AppLanguage.entries.find { it.code == tag } ?: AppLanguage.ENGLISH
    }
}
