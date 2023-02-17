package com.example.dedicas.core.util

import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class PhoneAuthHelper(private val context: Context) {

    fun verifyPhoneNumber() {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                TODO("Not yet implemented")
            }
        }
    }

}