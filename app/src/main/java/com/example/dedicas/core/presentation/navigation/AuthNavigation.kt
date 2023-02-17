package com.example.dedicas.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.feature_authentication.presentation.confirmation_screen.ConfirmationScreen
import com.example.dedicas.feature_authentication.presentation.home_screen.HomeScreen
import com.example.dedicas.feature_authentication.presentation.login_screen.LoginScreen
import com.example.dedicas.feature_authentication.presentation.otp_screen.OtpScreen
import com.example.dedicas.feature_authentication.presentation.otp_screen.OtpScreenViewModel
import com.example.dedicas.feature_authentication.presentation.signup_screen.SignupScreen
import com.example.dedicas.feature_authentication.presentation.sms_code_screen.SmsCodeScreen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

    }
}