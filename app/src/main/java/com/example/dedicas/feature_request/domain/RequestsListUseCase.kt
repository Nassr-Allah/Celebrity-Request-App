package com.example.dedicas.feature_request.domain

import android.util.Log
import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.toOfferRequest
import com.example.dedicas.core.data.models.OfferRequest
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.data.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RequestsListUseCase @Inject constructor(
    private val repository: RequestRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<List<OfferRequest>>> = flow {
        try {
            emit(Resource.Loading<List<OfferRequest>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                Log.d("RequestsListUseCase", authToken.token)
                val offers = repository.getClientOffers(authToken.token).map { it.toOfferRequest() }
                emit(Resource.Success(offers))
            } else {
                emit(Resource.Error<List<OfferRequest>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<List<OfferRequest>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<OfferRequest>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}