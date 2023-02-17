package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.Client
import com.google.gson.annotations.SerializedName

data class ClientDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("user")
    val user: UserDto? = null,
    @SerializedName("wilaya")
    val wilaya: String = ""
)

fun ClientDto.toClient(): Client {
    return Client(id, user?.toUser(), wilaya)
}
