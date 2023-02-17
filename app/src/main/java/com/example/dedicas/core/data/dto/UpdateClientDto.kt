package com.example.dedicas.core.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateClientDto(
    @SerializedName("user")
    val user: UpdateUserDto? = null
)
