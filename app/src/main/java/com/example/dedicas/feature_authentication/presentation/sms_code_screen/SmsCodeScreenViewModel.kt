package com.example.dedicas.feature_authentication.presentation.sms_code_screen

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.domain.AuthenticatePhoneUseCase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SmsCodeScreenViewModel @Inject constructor(
    private val useCase: AuthenticatePhoneUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SmsCodeScreenState())
    val state: State<SmsCodeScreenState> = _state

    val digit1 = mutableStateOf("")
    val digit2 = mutableStateOf("")
    val digit3 = mutableStateOf("")
    val digit4 = mutableStateOf("")
    val digit5 = mutableStateOf("")
    val digit6 = mutableStateOf("")
    val code = mutableStateOf("000000")

    fun validateSmsCode(verificationId: String): Boolean {
        val input = digit1.value + digit2.value + digit3.value + digit4.value + digit5.value + digit6.value
        val credential = PhoneAuthProvider.getCredential(verificationId, input)
        return input == credential.smsCode
    }

}