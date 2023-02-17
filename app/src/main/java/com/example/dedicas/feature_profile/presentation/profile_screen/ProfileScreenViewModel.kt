package com.example.dedicas.feature_profile.presentation.profile_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.data.dto.UpdateClientDto
import com.example.dedicas.core.data.dto.UpdateUserDto
import com.example.dedicas.core.data.dto.toClient
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.util.LanguageHelper
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_profile.domain.GetUserUseCase
import com.example.dedicas.feature_profile.domain.LogoutUserUseCase
import com.example.dedicas.feature_profile.domain.UpdateUserUseCase
import com.example.dedicas.feature_request.domain.RequestsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val requestsListUseCase: RequestsListUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ProfileScreenState())
    val state: State<ProfileScreenState> = _state

    val currentClient = mutableStateOf(Client())

    val firstName = mutableStateOf(currentClient.value.user?.firstName ?: "")
    val lastName = mutableStateOf(currentClient.value.user?.lastName ?: "")
    val email = mutableStateOf(currentClient.value.user?.email ?: "")

    val sentRequests = mutableStateOf(0)
    val pendingRequests = mutableStateOf(0)
    val acceptedRequests = mutableStateOf(0)

    val isInfoChanged = mutableStateOf(false)

    init {
        getCurrentUser()
        getRequests()
    }

    private fun getCurrentUser() {
        Log.d("ProfileScreenViewModel", "Called get user")
        getUserUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    currentClient.value = result.data?.body()?.toClient() ?: Client()
                    firstName.value = currentClient.value.user?.firstName ?: ""
                    lastName.value = currentClient.value.user?.lastName ?: ""
                    email.value = currentClient.value.user?.email ?: ""
                    _state.value = _state.value.copy(isLoading = false, getUserResponse = result.data)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRequests() {
        requestsListUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, requests = result.data ?: emptyList())
                    sentRequests.value = result.data?.size ?: 0
                    acceptedRequests.value = result.data?.count { it.status == "accepted" } ?: 0
                    pendingRequests.value = result.data?.count { it.status == "pending" } ?: 0
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser() {
        val updatedClient = UpdateClientDto(
            user = UpdateUserDto(
                firstName = firstName.value,
                lastName = lastName.value,
                email = email.value
            )
        )
        Log.d("ProfileScreenViewModel", updatedClient.toString())
        updateUserUseCase(currentClient.value.id!!, updatedClient).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = "")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, updateUserResponse = result.data, error = "")
                    val clientDto = result.data?.body()
                    Log.d("ProfileScreenViewModel", clientDto.toString())
                    currentClient.value = result.data?.body()?.toClient() ?: Client()
                    refreshInfo()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logoutUser() {
        logoutUserUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = "")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, logoutUserResponse = result.data, error = "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refreshInfo() {
        firstName.value = currentClient.value.user?.firstName ?: ""
        lastName.value = currentClient.value.user?.lastName ?: ""
        email.value = currentClient.value.user?.email ?: ""
    }

    fun checkInfoChange() {
        isInfoChanged.value = firstName.value != currentClient.value.user?.firstName ||
                lastName.value != currentClient.value.user?.lastName ||
                email.value != currentClient.value.user?.email
    }

}