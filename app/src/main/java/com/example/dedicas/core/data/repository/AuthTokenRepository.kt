package com.example.dedicas.core.data.repository

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.models.AuthToken

interface AuthTokenRepository {

    suspend fun getAuthToken(): AuthToken?

    suspend fun insertAuthToken(authToken: AuthToken)

    suspend fun deleteAuthToken()

}