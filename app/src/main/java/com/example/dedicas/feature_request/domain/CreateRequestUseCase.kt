package com.example.dedicas.feature_request.domain

import android.util.Log
import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.OfferRequestDto
import com.example.dedicas.core.data.dto.PostOfferRequestDto
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.data.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class CreateRequestUseCase @Inject constructor(
    private val repository: RequestRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(
        recepientId: Int,
        title: String,
        description: String
    ): Flow<Resource<Response<OfferRequestDto>>> = flow {
        try {
            emit(Resource.Loading<Response<OfferRequestDto>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                Log.d("CreateRequestUseCase", "${authToken.token}")
                val response = repository.createOffer(authToken.token, recepientId, title, description)
                Log.d("CreateRequestUseCase", response.toString())
                Log.d("CreateRequestUseCase", "$recepientId, $title, $description")
                if (response.isSuccessful && response.code() == 201) {
                    emit(Resource.Success(response))
                } else {
                    val message = response.message().ifEmpty { "Error ${response.code()}" }
                    emit(Resource.Error<Response<OfferRequestDto>>(message))
                }
            } else {
                emit(Resource.Error<Response<OfferRequestDto>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Response<OfferRequestDto>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response<OfferRequestDto>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}