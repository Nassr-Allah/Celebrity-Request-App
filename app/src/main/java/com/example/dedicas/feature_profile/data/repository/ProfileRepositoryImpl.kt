package com.example.dedicas.feature_profile.data.repository

import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.UpdateClientDto
import com.example.dedicas.core.data.dto.UserDto
import retrofit2.Response

class ProfileRepositoryImpl(val api: RestApi) : ProfileRepository {

    override suspend fun getCurrentClient(token: String): Response<ClientDto> {
        return api.getCurrentClient("Token $token")
    }

    override suspend fun updateClient(token: String, id: Int, clientDto: UpdateClientDto): Response<ClientDto> {
        return api.updateClient("Token $token", id, clientDto)
    }

    override suspend fun logoutUser(token: String): Response<Void> {
        return api.logoutUser("Token $token")
    }
}