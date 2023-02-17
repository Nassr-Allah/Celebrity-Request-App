package com.example.dedicas.core.data.models

import com.example.dedicas.core.data.dto.ClientDto

data class Client(
    val id: Int? = null,
    val user: User? = null,
    val wilaya: String = ""
)

fun Client.toClientDto(): ClientDto {
    return ClientDto(id, user?.toUserDto(), wilaya)
}
