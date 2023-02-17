package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.OfferRequest
import com.google.gson.annotations.SerializedName

data class OfferRequestDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: UserDto? = null,
    @SerializedName("payment")
    val payment: PaymentDto? = null,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = ""
)

fun OfferRequestDto.toOfferRequest(): OfferRequest {
    return OfferRequest(
        id,
        status,
        sender?.toUser(),
        recepient?.toUser(),
        payment?.toPayment(),
        title,
        description
    )
}
