package com.example.dedicas.feature_browsing.presentation.celeb_profile_screen

import com.example.dedicas.core.data.models.Celebrity

data class CelebProfileScreenState(
    val isLoading: Boolean = false,
    val celebrity: Celebrity? = null,
    val error: String = ""
)
