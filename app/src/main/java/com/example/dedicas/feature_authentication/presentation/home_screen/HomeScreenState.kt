package com.example.dedicas.feature_authentication.presentation.home_screen

import com.example.dedicas.core.data.models.AuthToken
import retrofit2.Response

data class HomeScreenState(
    val isLoading: Boolean = false,
    val authToken: AuthToken? = null,
    val error: String = ""
)
