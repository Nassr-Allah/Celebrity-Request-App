package com.example.dedicas.feature_authentication.presentation.sms_code_screen

data class SmsCodeScreenState(
    val isLoading: Boolean = false,
    val verificationId: String = "",
    val error: String = ""
)
