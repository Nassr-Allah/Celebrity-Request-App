package com.example.dedicas.feature_chat.domain

import com.example.dedicas.core.data.data_source.local.DatabaseDao
import com.example.dedicas.core.data.dto.toDiscussion
import com.example.dedicas.core.data.models.Discussion
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_chat.data.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class DiscussionsListUseCase @Inject constructor(
    private val repository: ChatRepository,
    private val authTokenRepository: AuthTokenRepository
) {

    operator fun invoke(): Flow<Resource<List<Discussion>>> = flow {
        try {
            emit(Resource.Loading<List<Discussion>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                val discussions = repository.getClientDiscussions(authToken.token).map { it.toDiscussion() }
                emit(Resource.Success(discussions))
            } else {
                emit(Resource.Error<List<Discussion>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error<List<Discussion>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Discussion>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}