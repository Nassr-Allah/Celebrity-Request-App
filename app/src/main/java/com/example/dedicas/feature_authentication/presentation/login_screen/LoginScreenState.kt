package com.example.dedicas.feature_authentication.presentation.login_screen

import com.example.dedicas.core.data.dto.AuthTokenDto
import retrofit2.Response

data class LoginScreenState(
    val isLoading: Boolean = false,
    val response: Response<AuthTokenDto>? = null,
    val error: String = ""
)
