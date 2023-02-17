package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoiceMessage(
    val id: Int? = null,
    val audio: String = ""
) : Parcelable
