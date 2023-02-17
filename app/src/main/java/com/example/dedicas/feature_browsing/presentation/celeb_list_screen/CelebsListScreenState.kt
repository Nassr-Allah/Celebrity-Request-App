package com.example.dedicas.feature_browsing.presentation.celeb_list_screen

import com.example.dedicas.core.data.models.Celebrity

data class CelebsListScreenState(
    val isLoading: Boolean = false,
    val celebrities: List<Celebrity> = emptyList(),
    val error: String = ""
)