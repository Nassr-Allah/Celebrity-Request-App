package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.AuthToken
import com.google.gson.annotations.SerializedName

data class AuthTokenDto(
    @SerializedName("expiry")
    val expiry: String = "",
    @SerializedName("token")
    val token: String = ""
)

fun AuthTokenDto.toAuthToken(): AuthToken {
    return AuthToken(expiry = expiry, token = token)
}
