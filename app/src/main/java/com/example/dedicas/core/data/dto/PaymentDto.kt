package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.Payment
import com.google.gson.annotations.SerializedName

data class PaymentDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("amount_paid")
    val amountPaid: Int = 0,
    @SerializedName("payment_date")
    val paymentDate: String = "",
    @SerializedName("is_valid")
    val isValid: Boolean = false,
    @SerializedName("receipt")
    val receipt: String? = "",
    @SerializedName("payment_status")
    val status: String? = ""
)

fun PaymentDto.toPayment(): Payment {
    return Payment(id, amountPaid, paymentDate, isValid, receipt, status)
}
