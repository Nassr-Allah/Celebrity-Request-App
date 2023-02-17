package com.example.dedicas

import android.app.Application
import android.app.LocaleManager
import android.content.res.Configuration
import com.example.dedicas.core.util.LanguageHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    fun initLanguage() {
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}