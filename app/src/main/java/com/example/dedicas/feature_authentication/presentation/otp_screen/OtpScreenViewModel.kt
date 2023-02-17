package com.example.dedicas.feature_authentication.presentation.otp_screen

import android.app.Activity
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.domain.AuthenticatePhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class OtpScreenViewModel @Inject constructor(
    private val useCase: AuthenticatePhoneUseCase
) : ViewModel() {

    private val _state = mutableStateOf(OtpScreenState())
    val state: State<OtpScreenState> = _state

    val phoneNumber = mutableStateOf("")
    val isPhoneNumberValid = mutableStateOf(true)

    fun validatePhoneNumber(): Boolean {
        Log.d("OtpViewModel", phoneNumber.value)
        isPhoneNumberValid.value =
            Patterns.PHONE.matcher("+213${phoneNumber.value}").matches() &&
            phoneNumber.value.length == 9
        Log.d("OtpViewModel", "number: " + phoneNumber.value)
        return isPhoneNumberValid.value
    }

    fun verifyPhoneNumber(activity: Activity) {
        useCase(activity, "+213${phoneNumber.value}").onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, verificationId = result.data ?: "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}