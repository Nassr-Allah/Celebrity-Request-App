package com.example.dedicas.feature_profile.domain

import android.util.Log
import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.ClientDto
import com.example.dedicas.core.data.dto.UpdateClientDto
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.data.models.toClientDto
import com.example.dedicas.core.data.models.toUserDto
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_profile.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val repository: ProfileRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(clientId: Int, client: UpdateClientDto): Flow<Resource<Response<ClientDto>>> =
        flow {
            try {
                emit(Resource.Loading<Response<ClientDto>>())
                Log.d("UpdateUserUseCase", "Loading...")
                val authToken = authTokenRepository.getAuthToken()
                if (authToken != null) {
                    Log.d("UpdateUserUseCase", authToken.token)
                    val response = repository.updateClient(authToken.token, clientId, client)
                    Log.d("UpdateUserUseCase", "response: $response")
                    if (response.isSuccessful && response.code() == 200) {
                        Log.d("UpdateUserUseCase", "Success")
                        emit(Resource.Success(response))
                    } else {
                        val message = response.message().ifEmpty { "Error ${response.code()}" }
                        Log.d("UpdateUserUseCase", "Error")
                        emit(Resource.Error<Response<ClientDto>>(message))
                    }
                } else {
                    Log.d("UpdateUserUseCase", "null token")
                    emit(Resource.Error<Response<ClientDto>>("Unauthorized, try to login again"))
                }
            } catch (e: HttpException) {
                emit(Resource.Error<Response<ClientDto>>(e.localizedMessage ?: "Unexpected Server Error"))
            } catch (e: IOException) {
                emit(Resource.Error<Response<ClientDto>>(e.localizedMessage ?: "Unexpected Error"))
            }
        }

}