package com.example.dedicas.feature_profile.data.repository

import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.UpdateClientDto
import com.example.dedicas.core.data.dto.UserDto
import retrofit2.Response

interface ProfileRepository {

    suspend fun getCurrentClient(token: String): Response<ClientDto>

    suspend fun updateClient(token: String, id: Int, clientDto: UpdateClientDto): Response<ClientDto>

    suspend fun logoutUser(token: String): Response<Void>

}