package com.example.dedicas.feature_authentication.domain

import android.util.Base64
import android.util.Log
import com.example.dedicas.core.data.dto.AuthTokenDto
import com.example.dedicas.core.data.dto.toAuthToken
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(phoneNumber: String, password: String): Flow<Resource<Response<AuthTokenDto>>> = flow {
        try {
            emit(Resource.Loading<Response<AuthTokenDto>>())
            Log.d("LoginUseCase", "Loading...")
            val credentials = "+213$phoneNumber:$password"
            Log.d("LoginUseCase", "${credentials.toByteArray()}")
            val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
            val response = repository.loginUser("Basic $encodedCredentials")
            Log.d("LoginUseCase", "Started Call: $response")
            if (response.isSuccessful) {
                val authToken = response.body()?.toAuthToken()
                if (authToken != null) {
                    authTokenRepository.insertAuthToken(authToken)
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error<Response<AuthTokenDto>>("Error"))
                }
            } else {
                val message = response.message().ifEmpty { "Error" }
                Log.d("LoginUseCase", message)
                emit(Resource.Error<Response<AuthTokenDto>>(message))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}