package com.example.dedicas.core.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserDto(
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("email")
    val email: String = ""
)
