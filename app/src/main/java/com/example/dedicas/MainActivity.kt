package com.example.dedicas

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.dedicas.core.presentation.navigation.AuthNavigation
import com.example.dedicas.core.presentation.navigation.MainAppNavigation
import com.example.dedicas.core.util.LanguageHelper
import com.example.dedicas.ui.theme.DedicasTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val language = LanguageHelper.getUserLanguage(this)
        LanguageHelper.updateLanguage(this, language)

        setContent {
            DedicasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.auth)
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
