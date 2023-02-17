package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("password")
    val password: String = "",
    @SerializedName("username")
    val username: String? = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("phone_number")
    val phoneNumber: String = "",
    @SerializedName("payment_details")
    val paymentDetailsDto: PaymentDetailsDto? = null
)

fun UserDto.toUser(): User {
    return User(id, password, username, firstName, lastName, email, phoneNumber)
}
