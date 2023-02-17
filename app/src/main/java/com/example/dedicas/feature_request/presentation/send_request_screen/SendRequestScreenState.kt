package com.example.dedicas.feature_request.presentation.send_request_screen

import com.example.dedicas.core.data.dto.OfferRequestDto
import retrofit2.Response

data class SendRequestScreenState(
    val isLoading: Boolean = false,
    val response: Response<OfferRequestDto>? = null,
    val error: String = ""
)
