package com.example.dedicas.core.data.models

data class Payment(
    val id: Int? = null,
    val amountPaid: Int = 0,
    val paymentDate: String = "",
    val isValid: Boolean = false,
    val receipt: String? = "",
    val status: String? = ""
)
