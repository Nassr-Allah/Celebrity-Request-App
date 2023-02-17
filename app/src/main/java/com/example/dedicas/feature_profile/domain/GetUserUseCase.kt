package com.example.dedicas.feature_profile.domain

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.toClient
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<Response<ClientDto>>> = flow {
        try {
            emit(Resource.Loading<Response<ClientDto>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                val response = repository.getCurrentClient(authToken.token)
                if (response.isSuccessful && response.code() == 200) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error<Response<ClientDto>>(response.message()))
                }
            } else {
                emit(Resource.Error<Response<ClientDto>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<ClientDto>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<ClientDto>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}