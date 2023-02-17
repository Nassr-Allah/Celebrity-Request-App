package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageMessage(
    val id: Int? = null,
    val image: String = ""
) : Parcelable
