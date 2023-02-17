package com.example.dedicas.feature_chat.presentation.chat_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_chat.domain.DiscussionsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val discussionsListUseCase: DiscussionsListUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ChatListScreenState())
    val state: State<ChatListScreenState> = _state

    init {
        getDiscussions()
    }

    private fun getDiscussions() {
        discussionsListUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = "")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, discussions = result.data ?: emptyList(), error = "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

}