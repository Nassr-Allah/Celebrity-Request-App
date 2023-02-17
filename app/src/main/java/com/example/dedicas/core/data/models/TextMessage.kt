package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextMessage(
    val id: Int? = null,
    val message: String = ""
) : Parcelable
