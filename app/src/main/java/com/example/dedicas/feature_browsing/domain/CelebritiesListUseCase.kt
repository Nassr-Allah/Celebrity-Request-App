package com.example.dedicas.feature_browsing.domain

import com.example.dedicas.core.data.data_source.local.DatabaseDao
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
import javax.inject.Inject

class CelebritiesListUseCase @Inject constructor(
    private val repository: BrowsingRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<List<Celebrity>>> = flow {
        try {
            emit(Resource.Loading<List<Celebrity>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                val celebrities = repository.getCelebrityList(authToken.token).map { it.toCelebrity() }
                emit(Resource.Success(celebrities))
            } else {
                emit(Resource.Error<List<Celebrity>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<List<Celebrity>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Celebrity>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}