package com.example.dedicas.feature_browsing.presentation.celeb_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.data.models.Celebrity
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_browsing.domain.CelebritiesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CelebsListViewModel @Inject constructor(
    private val useCase: CelebritiesListUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CelebsListScreenState())
    val state: State<CelebsListScreenState> = _state

    init {
        getCelebritiesList()
    }

    private fun getCelebritiesList() {
        useCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, celebrities = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}