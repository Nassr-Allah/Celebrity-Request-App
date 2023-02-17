package com.example.dedicas.feature_profile.presentation.profile_screen

import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.models.OfferRequest
import retrofit2.Response

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val getUserResponse: Response<ClientDto>? = null,
    val updateUserResponse: Response<ClientDto>? = null,
    val logoutUserResponse: Response<Void>? = null,
    val requests: List<OfferRequest> = emptyList(),
    val error: String = ""
)