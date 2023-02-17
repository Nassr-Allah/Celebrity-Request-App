package com.example.dedicas.core.data.dto

import com.google.gson.annotations.SerializedName

data class PostOfferRequestDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("status")
    val status: String = "null",
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: Int = 0,
    @SerializedName("payment")
    val payment: PaymentDto? = null,
    @SerializedName("title")
    val title: String = "null",
    @SerializedName("description")
    val description: String = "null"
)
