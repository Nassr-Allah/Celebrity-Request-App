package com.example.dedicas.core.data.repository

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.models.AuthToken
import javax.inject.Inject

class AuthTokenRepositoryImpl @Inject constructor(
    private val dao: DatabaseDao
) : AuthTokenRepository {

    override suspend fun getAuthToken(): AuthToken? {
        return dao.getCachedToken()
    }

    override suspend fun insertAuthToken(authToken: AuthToken) {
        dao.insertTokenInDb(authToken)
    }

    override suspend fun deleteAuthToken() {
        dao.deleteCachedToken()
    }
}