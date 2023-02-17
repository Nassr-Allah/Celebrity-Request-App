package com.example.dedicas.feature_authentication.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val useCase: LoginUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    val phoneNumber = mutableStateOf("")
    val password = mutableStateOf("")

    val isPhoneNumberValid = mutableStateOf(true)
    val isPasswordValid = mutableStateOf(true)

    fun loginUser() {
        useCase(phoneNumber.value, password.value).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, response = result.data)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validateInput(): Boolean {
        isPhoneNumberValid.value = phoneNumber.value.isNotEmpty()
        isPasswordValid.value = password.value.isNotEmpty()

        return isPhoneNumberValid.value && isPasswordValid.value
    }

}