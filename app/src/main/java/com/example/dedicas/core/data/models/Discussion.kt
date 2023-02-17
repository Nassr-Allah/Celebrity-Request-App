package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Discussion(
    val id: Int? = null,
    val members: List<User> = emptyList(),
    val messages: List<ChatMessageInfo> = emptyList()
) : Parcelable
