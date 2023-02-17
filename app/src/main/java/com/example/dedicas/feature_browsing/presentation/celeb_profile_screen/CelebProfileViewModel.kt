package com.example.dedicas.feature_browsing.presentation.celeb_profile_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.data.models.Celebrity
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_browsing.domain.GetCelebrityUseCase
import com.example.dedicas.feature_browsing.presentation.celeb_list_screen.CelebsListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CelebProfileViewModel @Inject constructor(
    private val useCase: GetCelebrityUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CelebProfileScreenState())
    val state: State<CelebProfileScreenState> = _state

    init {
        savedStateHandle.get<String>("celeb_id")?.let { id ->
            Log.d("celeb_id", id)
            getCelebrity(id.toInt())
        }
    }

    private fun getCelebrity(id: Int) {
        useCase(id).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, celebrity = result.data)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}