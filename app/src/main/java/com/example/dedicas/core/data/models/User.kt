package com.example.dedicas.core.data.models

import android.os.Parcelable
import com.example.dedicas.core.data.dto.UserDto
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val password: String = "",
    val username: String? = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val paymentDetails: PaymentDetails? = null
) : Parcelable

fun User.toUserDto(): UserDto {
    return UserDto(
        id,
        password,
        username,
        firstName,
        lastName,
        email,
        phoneNumber,
        paymentDetails?.toPaymentDetailsDto()
    )
}
