package com.example.dedicas.core.presentation.routes

import androidx.annotation.StringRes
import com.example.dedicas.R

sealed class Screen(val route: String, @StringRes val id: Int? = null) {
    //Authentication
    object HomeScreen : Screen("home_screen")
    object LoginScreen : Screen("login_screen")
    object SignupScreen : Screen("signup_screen")
    object OtpScreen : Screen("otp_screen")
    object SmsCodeScreen : Screen("sms_code_screen")
    object ConfirmationScreen : Screen("confirmation_screen")

    //Browsing
    object CelebListScreen : Screen("celeb_list_screen", R.string.celebrities)
    object CelebProfileScreen : Screen("celeb_profile_screen")

    //Chat
    object ChatListScreen : Screen("chat_list_screen", R.string.chat)
    object ChatScreen : Screen("chat_screen")

    //Profile
    object ProfileScreen : Screen("profile_screen", R.string.profile)

    //Request
    object RequestsListScreen : Screen("request_list_screen", R.string.requests)
    object SendRequestScreen : Screen("send_request_screen")
}
