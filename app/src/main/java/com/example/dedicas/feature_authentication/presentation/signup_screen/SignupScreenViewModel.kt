package com.example.dedicas.feature_authentication.presentation.signup_screen

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class SignupScreenViewModel : ViewModel() {

    val firstName = mutableStateOf("")
    val lastName = mutableStateOf("")
    val email = mutableStateOf("")

    val isFirstNameValid = mutableStateOf(true)
    val isLastNameValid = mutableStateOf(true)
    val isEmailValid = mutableStateOf(true)

    fun validateInput(): Boolean {
        Log.d("SignupViewModel", firstName.value + " " + lastName.value)
        isFirstNameValid.value = firstName.value.isNotEmpty()
        isLastNameValid.value = lastName.value.isNotEmpty()
        isEmailValid.value = Patterns.EMAIL_ADDRESS.matcher(email.value).matches()

        return isFirstNameValid.value && isLastNameValid.value && isEmailValid.value
    }

}