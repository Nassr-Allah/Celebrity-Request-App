package com.example.dedicas.feature_authentication.domain

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.models.AuthToken
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val repository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<AuthToken>> = flow {
        try {
            emit(Resource.Loading<AuthToken>())
            val authToken = repository.getAuthToken()
            if (authToken != null) {
                emit(Resource.Success(authToken))
            } else {
                emit(Resource.Error<AuthToken>("Empty token"))
            }
        } catch (e: Exception) {
            emit(Resource.Error<AuthToken>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}