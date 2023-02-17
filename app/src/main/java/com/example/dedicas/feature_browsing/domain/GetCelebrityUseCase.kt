package com.example.dedicas.feature_browsing.domain

import android.util.Log
import com.example.dedicas.core.data.dto.CelebrityDto
import com.example.dedicas.core.data.dto.toCelebrity
import com.example.dedicas.core.data.models.Celebrity
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_browsing.data.BrowsingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class GetCelebrityUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val browsingRepository: BrowsingRepository
) {

    operator fun invoke(id: Int): Flow<Resource<Celebrity>> = flow {
        try {
            emit(Resource.Loading<Celebrity>())
            val authToken = authTokenRepository.getAuthToken()
            Log.d("AuthToken", authToken?.token.toString())
            if (authToken != null) {
                val response = browsingRepository.getCelebrityById(authToken.token, id)
                if (response.isSuccessful) {
                    val celebrity = response.body()?.toCelebrity()
                    emit(Resource.Success(celebrity))
                } else {
                    val message = response.message().ifEmpty { "Error" }
                    emit(Resource.Error<Celebrity>(message))
                }
            } else {
                emit(Resource.Error<Celebrity>("Unauthorized"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<Celebrity>(e.message ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Celebrity>(e.message ?: "Unexpected Error"))
        }
    }

}