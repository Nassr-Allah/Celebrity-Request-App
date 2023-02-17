package com.example.dedicas.feature_profile.domain

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<Response<Void>>> = flow {
        try {
            emit(Resource.Loading<Response<Void>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                val response = repository.logoutUser(authToken.token)
                if (response.isSuccessful && response.code() == 204) {
                    authTokenRepository.deleteAuthToken()
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error<Response<Void>>(response.message()))
                }
            } else {
                emit(Resource.Error<Response<Void>>("Unauthorized"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<Void>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}