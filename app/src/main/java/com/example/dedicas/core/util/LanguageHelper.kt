package com.example.dedicas.core.util

import android.content.Context
import android.content.res.Configuration
import com.example.dedicas.core.util.Constants.Companion.GENERAL_STORAGE
import com.example.dedicas.core.util.Constants.Companion.KEY_USER_LANGUAGE
import java.util.*

class LanguageHelper {

    companion object {

        fun updateLanguage(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val res = context.resources
            val config = Configuration(res.configuration)
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
            context.createConfigurationContext(config)
        }

        fun storeLanguage(context: Context, language: String) {
            context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_USER_LANGUAGE, language)
                .apply()
        }

        fun getUserLanguage(context: Context): String {
            val defaultLanguage = Locale.getDefault().language
            return context.getSharedPreferences(GENERAL_STORAGE, Context.MODE_PRIVATE)
                .getString(KEY_USER_LANGUAGE, null) ?: defaultLanguage
        }

    }

}
