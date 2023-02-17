package com.example.dedicas.feature_authentication.domain

import android.util.Log
import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.AuthTokenDto
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.toAuthToken
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.data.models.toClientDto
import com.example.dedicas.core.data.models.toUserDto
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class CreateClientUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(client: Client): Flow<Resource<Response<AuthTokenDto>>> = flow {
        try {
            emit(Resource.Loading<Response<AuthTokenDto>>())
            Log.d("CreateClientUseCase", "Loading...")
            Log.d("CreateClientUseCase", "$client")
            val response = repository.createClient(client.toClientDto())
            Log.d("CreateClientUseCase", "Call made: $response")
            val authToken = response.body()?.toAuthToken()
            Log.d("CreateClientUseCase", "$authToken")
            if (authToken != null) {
                authTokenRepository.insertAuthToken(authToken)
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error<Response<AuthTokenDto>>("Failed to create a profile"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<AuthTokenDto>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<AuthTokenDto>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}