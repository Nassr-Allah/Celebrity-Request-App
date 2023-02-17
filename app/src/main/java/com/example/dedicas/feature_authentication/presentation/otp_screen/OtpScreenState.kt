package com.example.dedicas.feature_authentication.presentation.otp_screen

data class OtpScreenState(
    val isLoading: Boolean = true,
    val verificationId: String = "",
    val error: String = ""
)
