package com.example.dedicas.core.data.data_source.remote

import com.example.dedicas.core.data.dto.*
import com.example.dedicas.core.data.models.AuthToken
import com.example.dedicas.core.data.models.Celebrity
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface RestApi {

    @POST("auth/login/")
    suspend fun loginUser(@Header("Authorization") authorization: String): Response<AuthTokenDto>

    @POST("auth/logout/")
    suspend fun logoutUser(@Header("Authorization") token: String): Response<Void>

    @GET("client/")
    suspend fun getCurrentClient(@Header("Authorization") token: String): Response<ClientDto>

    @POST("client/create/")
    suspend fun createClient(@Body clientDto: ClientDto): Response<AuthTokenDto>

    @PATCH("client/{id}/")
    suspend fun updateClient(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body clientDto: UpdateClientDto
    ): Response<ClientDto>

    @GET("client/offers/")
    suspend fun getClientOffers(@Header("Authorization") token: String): List<OfferRequestDto>

    @FormUrlEncoded
    @POST("client/offers/")
    suspend fun createOffer(
        @Header("Authorization") token: String,
        @Field("recepient") recepientId: Int,
        @Field("title") title: String,
        @Field("description") description: String
    ): Response<OfferRequestDto>

    @Multipart
    @PATCH("client/offers/{id}/payment/")
    suspend fun createPayment(
        @Header("Authorization") token: String,
        @Path("id") offerId: Int,
        @Part image: MultipartBody.Part
    ): Response<PaymentDto>

    @Multipart
    @PATCH("client/offers/{id}/payment/")
    suspend fun updateReceipt(
        @Header("Authorization") token: String,
        @Path("id") offerId: Int,
        @Part image: MultipartBody.Part
    ): Response<Void>

    @GET("celebs/")
    suspend fun getCelebrityList(@Header("Authorization") token: String): List<CelebrityDto>

    @GET("celebs/{id}/")
    suspend fun getCelebrityById(@Header("Authorization") token: String, @Path("id") id: Int): Response<CelebrityDto>

    @GET("chat/discussions/{id}/")
    suspend fun getDiscussionMessages(
        @Header("Authorization") token: String,
        @Path("id") discussionId: Int
    ): List<ChatMessageInfoDto>

    @GET("chat/discussions/")
    suspend fun getClientDiscussions(@Header("Authorization") token: String): List<DiscussionDto>

}