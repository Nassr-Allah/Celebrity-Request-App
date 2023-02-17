package com.example.dedicas.feature_authentication.data.repository

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.dto.AuthTokenDto
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.UserDto
import com.example.dedicas.core.data.models.AuthToken
import com.example.dedicas.core.data.models.Client
import retrofit2.Response

class AuthRepositoryImpl(val api: RestApi, val dao: DatabaseDao) : AuthRepository {

    override suspend fun loginUser(authorization: String): Response<AuthTokenDto> {
        return api.loginUser(authorization)
    }

    override suspend fun createClient(clientDto: ClientDto): Response<AuthTokenDto> {
        return api.createClient(clientDto)
    }

    override suspend fun insertTokenInDb(authToken: AuthToken) {
        dao.insertTokenInDb(authToken)
    }

    override suspend fun retrieveCachedToken(): AuthToken? {
        return dao.getCachedToken()
    }
}