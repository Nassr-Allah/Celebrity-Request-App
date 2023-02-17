package com.example.dedicas.feature_request.presentation.send_request_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.domain.CreateRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SendRequestViewModel @Inject constructor(
    private val useCase: CreateRequestUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SendRequestScreenState())
    val state: State<SendRequestScreenState> = _state

    val title = mutableStateOf("")
    val description = mutableStateOf("")

    val isTitleValid = mutableStateOf(true)
    val isDescriptionValid = mutableStateOf(true)

    fun createRequest(recepientId: Int) {
        useCase(recepientId, title.value, description.value).onEach { result ->
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
        isTitleValid.value = title.value.isNotEmpty()
        isDescriptionValid.value = title.value.isNotEmpty()

        return isTitleValid.value && isDescriptionValid.value
    }

}