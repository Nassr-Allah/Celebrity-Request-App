package com.example.dedicas.feature_authentication.presentation.confirmation_screen

import com.example.dedicas.core.data.dto.AuthTokenDto
import com.example.dedicas.core.data.dto.ClientDto
import retrofit2.Response

data class ConfirmationScreenState(
    val isLoading: Boolean = false,
    val response: Response<AuthTokenDto>? = null,
    val error: String = ""
)