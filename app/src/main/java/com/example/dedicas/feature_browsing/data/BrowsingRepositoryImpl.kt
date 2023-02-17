package com.example.dedicas.feature_browsing.data

import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.dto.CelebrityDto
import retrofit2.Response

class BrowsingRepositoryImpl(val api: RestApi) : BrowsingRepository {

    override suspend fun getCelebrityList(token: String): List<CelebrityDto> {
        return api.getCelebrityList("Token $token")
    }

    override suspend fun getCelebrityById(token: String, id: Int): Response<CelebrityDto> {
        return api.getCelebrityById("Token $token", id)
    }
}