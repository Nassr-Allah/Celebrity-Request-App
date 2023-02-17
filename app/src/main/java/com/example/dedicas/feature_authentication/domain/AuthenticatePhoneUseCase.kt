package com.example.dedicas.feature_authentication.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.data.repository.RequestRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthenticatePhoneUseCase @Inject constructor(
    private val repository: RequestRepository
) {

    operator fun invoke(activity: Activity, phoneNumber: String): Flow<Resource<String>> = callbackFlow {
        Log.d("PhoneAuthCallbacks", "started ...")
        trySend(Resource.Loading<String>())
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d("PhoneAuthCallbacks", "code sent: ${credential.smsCode}")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                trySend(Resource.Error<String>(e.localizedMessage ?: "Unexpected Error"))
                Log.d("PhoneAuthCallbacks", e.localizedMessage ?: "zebi")
                cancel(CancellationException(e.localizedMessage))
            }

            override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.d("PhoneAuthCallbacks", "code sent")
                trySend(Resource.Success(verificationId))
                channel.close()
            }
        }

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        Log.d("PhoneAuthCallbacks", "verifying...")
        PhoneAuthProvider.verifyPhoneNumber(options)

        awaitClose { channel.close() }
    }

}