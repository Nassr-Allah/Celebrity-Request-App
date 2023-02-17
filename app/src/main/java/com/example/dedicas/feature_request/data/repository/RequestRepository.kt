package com.example.dedicas.feature_request.data.repository

import android.media.Image
import com.example.dedicas.core.data.dto.OfferRequestDto
import com.example.dedicas.core.data.dto.PaymentDto
import com.example.dedicas.core.data.dto.PostOfferRequestDto
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.File

interface RequestRepository {

    suspend fun getClientOffers(token: String): List<OfferRequestDto>

    suspend fun createOffer(
        token: String,
        recepientId: Int,
        title: String,
        description: String
    ): Response<OfferRequestDto>

    suspend fun createPayment(
        token: String,
        offerId: Int,
        image: MultipartBody.Part
    ): Response<PaymentDto>

    suspend fun updateReceipt(token: String, offerId: Int, image: MultipartBody.Part): Response<Void>

}