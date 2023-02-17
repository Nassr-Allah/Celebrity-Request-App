package com.example.dedicas.feature_authentication.presentation.confirmation_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_authentication.domain.CreateClientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ConfirmationScreenViewModel @Inject constructor(
    private val useCase: CreateClientUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ConfirmationScreenState())
    val state: State<ConfirmationScreenState> = _state

    val password = mutableStateOf("")
    val confirmedPassword = mutableStateOf("")

    val isPasswordMatch = mutableStateOf(true)
    val isPasswordLengthCorrect = mutableStateOf(true)

    val user = mutableStateOf(User())

    fun createClient() {
        user.value = user.value.copy(password = password.value)
        val client = Client(user = user.value, wilaya = "25")
        Log.d("ConfirmationViewModel", "client is: $client")
        useCase(client).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d("ConfirmationViewModel", "Loading...")
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    Log.d("ConfirmationViewModel", "Success")
                    _state.value = _state.value.copy(isLoading = false, response = result.data)
                }
                is Resource.Error -> {
                    Log.d("ConfirmationViewModel", "${result.message}")
                    Log.d("ConfirmationViewModel", "${_state.value}")
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unexpected Error",
                        response = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun validatePassword(): Boolean {
        isPasswordMatch.value = password.value == confirmedPassword.value
        isPasswordLengthCorrect.value = password.value.length >= 8
        return isPasswordMatch.value && isPasswordLengthCorrect.value
    }

}