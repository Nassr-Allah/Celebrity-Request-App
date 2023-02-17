package com.example.dedicas.feature_request.domain

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.dedicas.core.data.dto.PaymentDto
import com.example.dedicas.core.data.models.OfferRequest
import com.example.dedicas.core.data.repository.AuthTokenRepository
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.data.repository.RequestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class CreatePaymentUseCase @Inject constructor(
    private val repository: RequestRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val getFilePathUseCase: GetFilePathUseCase
) {

    operator fun invoke(
        offer: OfferRequest,
        uri: Uri,
        contentResolver: ContentResolver,
        context: Context
    ): Flow<Resource<Response<PaymentDto>>> = flow {
        try {
            emit(Resource.Loading<Response<PaymentDto>>())
            val authToken = authTokenRepository.getAuthToken()
            if (authToken != null) {
                val file = getFilePathUseCase(uri, contentResolver, context)
                Log.d("CreatePaymentUseCase", file?.path.toString())
                if (file == null) {
                    emit(Resource.Error<Response<PaymentDto>>("File not found"))
                    return@flow
                }
                val image = MultipartBody.Part.createFormData(
                    "image",
                    file.name,
                    file.asRequestBody()
                )
                Log.d("CreatePaymentUseCase", file.path.toString())
                Log.d("CreatePaymentUseCase", "uploading image: ${image.body}")
                val date = System.currentTimeMillis()
                val response = repository.createPayment(authToken.token, offer.payment?.id ?: 0, image)
                Log.d("CreatePaymentUseCase", "response: $response")
                if (response.isSuccessful) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error<Response<PaymentDto>>(response.message()))
                }
            } else {
                emit(Resource.Error<Response<PaymentDto>>("Unauthorized, try to login again"))
            }
        } catch (e: HttpException) {
            Log.d("CreatePaymentUseCase", "http error: ${e.localizedMessage}")
            emit(Resource.Error<Response<PaymentDto>>(e.localizedMessage ?: "Unexpected Server Error"))
        } catch (e: IOException) {
            Log.d("CreatePaymentUseCase", "IO error: ${e.localizedMessage}")
            emit(Resource.Error<Response<PaymentDto>>(e.localizedMessage ?: "Unexpected Error"))
        }
    }

}