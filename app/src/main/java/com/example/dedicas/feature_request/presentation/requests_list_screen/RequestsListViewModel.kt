package com.example.dedicas.feature_request.presentation.requests_list_screen

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dedicas.core.data.models.OfferRequest
import com.example.dedicas.core.util.Resource
import com.example.dedicas.feature_request.domain.CreatePaymentUseCase
import com.example.dedicas.feature_request.domain.RequestsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RequestsListViewModel @Inject constructor(
    private val requestsListUseCase: RequestsListUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(RequestsListScreenState())
    val state: State<RequestsListScreenState> = _state

    private val allRequests = mutableStateOf<List<OfferRequest>>(emptyList())
    val filteredRequests = mutableStateOf<List<OfferRequest>>(emptyList())

    val offerId = mutableStateOf(0)
    val offer = mutableStateOf(OfferRequest())
    val uri = mutableStateOf<Uri?>(null)

    init {
        getRequests()
    }

    private fun getRequests() {
        requestsListUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    allRequests.value = result.data ?: emptyList()
                    filteredRequests.value = result.data ?: emptyList()
                    _state.value = _state.value.copy(isLoading = false, requests = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun createPayment(contentResolver: ContentResolver, context: Context) {
        createPaymentUseCase(offer.value, uri.value!!, contentResolver, context).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true, error = "")
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isLoading = false, response = result.data, error = "")
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false, error = result.message ?: "Unexpected Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterRequests(query: String) {
        if (query == "all") {
            filteredRequests.value = allRequests.value
        } else if (query == "payment rejected") {
            filteredRequests.value = allRequests.value
            filteredRequests.value = filteredRequests.value.filter { it.payment?.status == "refused" }
        } else {
            filteredRequests.value = allRequests.value
            filteredRequests.value = filteredRequests.value.filter { it.status == query }
            Log.d("RequestsListViewModel", "${filteredRequests.value}")
        }
    }

}