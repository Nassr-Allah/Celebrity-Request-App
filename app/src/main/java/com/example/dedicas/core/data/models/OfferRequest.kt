package com.example.dedicas.core.data.models

data class OfferRequest(
    val id: Int? = null,
    val status: String = "",
    val sender: User? = null,
    val recepient: User? = null,
    val payment: Payment? = null,
    val title: String = "",
    val description: String = ""
)
