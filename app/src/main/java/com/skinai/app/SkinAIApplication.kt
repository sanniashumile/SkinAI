package com.skinai.app

import android.app.Application
import com.skinai.app.data.repository.SettingsStore
import com.skinai.app.data.repository.SkinAnalysisRepository
import com.skinai.app.util.ConnectivityObserver

class SkinAIApplication : Application() {

    lateinit var settingsStore: SettingsStore
        private set

    lateinit var connectivityObserver: ConnectivityObserver
        private set

    lateinit var analysisRepository: SkinAnalysisRepository
        private set

    override fun onCreate() {
        super.onCreate()
        settingsStore = SettingsStore(this)
        connectivityObserver = ConnectivityObserver(this)
        analysisRepository = SkinAnalysisRepository(this, connectivityObserver, settingsStore)
    }

    override fun onTerminate() {
        super.onTerminate()
        analysisRepository.release()
    }
}
