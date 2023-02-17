package com.example.dedicas.feature_authentication.data.repository

import com.example.dedicas.core.data.dto.AuthTokenDto
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.UserDto
import com.example.dedicas.core.data.models.AuthToken
import retrofit2.Response

interface AuthRepository {

    suspend fun loginUser(authorization: String): Response<AuthTokenDto>

    suspend fun createClient(clientDto: ClientDto): Response<AuthTokenDto>

    suspend fun insertTokenInDb(authToken: AuthToken)

    suspend fun retrieveCachedToken(): AuthToken?

}