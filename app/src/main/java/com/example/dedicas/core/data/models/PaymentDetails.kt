package com.example.dedicas.core.data.models

import android.os.Parcelable
import com.example.dedicas.core.data.dto.PaymentDetailsDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentDetails(
    val id: Int? = null,
    val ccp: String = "",
    val rip: String = "",
    val address: String = ""
) : Parcelable

fun PaymentDetails.toPaymentDetailsDto(): PaymentDetailsDto {
    return PaymentDetailsDto(id, ccp, rip, address)
}
