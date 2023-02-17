package com.example.dedicas.feature_request.presentation.requests_list_screen

import com.example.dedicas.core.data.dto.PaymentDto
import com.example.dedicas.core.data.models.OfferRequest
import retrofit2.Response

data class RequestsListScreenState(
    val isLoading: Boolean = false,
    val requests: List<OfferRequest> = emptyList(),
    val response: Response<PaymentDto>? = null,
    val error: String = ""
)
