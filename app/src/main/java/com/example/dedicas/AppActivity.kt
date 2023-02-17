package com.example.dedicas

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.dedicas.core.presentation.navigation.MainAppNavigation
import com.example.dedicas.core.util.LanguageHelper
import com.example.dedicas.ui.theme.DedicasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)

        setContent {
            val context = LocalContext.current
            CompositionLocalProvider(LocalMutableContext provides remember { mutableStateOf(context) }) {
                CompositionLocalProvider(LocalContext provides LocalMutableContext.current.value) {
                    DedicasTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.White
                        ) {
                            MainAppNavigation()
                        }
                    }
                }
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)
    }
}

val LocalMutableContext = staticCompositionLocalOf<MutableState<Context>> {
    error("Context not provided")
}