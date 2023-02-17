package com.example.dedicas.feature_browsing.data

import com.example.dedicas.core.data.dto.CelebrityDto
import retrofit2.Response

interface BrowsingRepository {

    suspend fun getCelebrityList(token: String): List<CelebrityDto>

    suspend fun getCelebrityById(token: String, id: Int): Response<CelebrityDto>

}