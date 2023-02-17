package com.example.dedicas.feature_request.data.repository

import android.media.Image
import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.dto.OfferRequestDto
import com.example.dedicas.core.data.dto.PaymentDto
import com.example.dedicas.core.data.dto.PostOfferRequestDto
import okhttp3.MultipartBody
import retrofit2.Response

class RequestRepositoryImpl(val api: RestApi) : RequestRepository {

    override suspend fun getClientOffers(token: String): List<OfferRequestDto> {
        return api.getClientOffers("Token $token")
    }

    override suspend fun createOffer(
        token: String,
        recepientId: Int,
        title: String,
        description: String
    ): Response<OfferRequestDto> {
        return api.createOffer("Token $token", recepientId, title, description)
    }

    override suspend fun createPayment(
        token: String,
        offerId: Int,
        image: MultipartBody.Part
    ): Response<PaymentDto> {
        return api.createPayment("Token $token", offerId, image)
    }

    override suspend fun updateReceipt(token: String, offerId: Int, image: MultipartBody.Part): Response<Void> {
        return api.updateReceipt("Token $token", offerId, image)
    }
}