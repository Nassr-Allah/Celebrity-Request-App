package com.example.dedicas.core.data.dto

import com.google.gson.annotations.SerializedName

data class PaymentDetailsDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("ccp")
    val ccp: String = "",
    @SerializedName("rip")
    val rip: String = "",
    @SerializedName("address")
    val address: String = ""
)
