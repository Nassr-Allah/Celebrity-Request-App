package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Celebrity(
    val id: Int? = null,
    val price: Int = 0,
    val isAvailable: Boolean = true,
    val user: User? = null,
    val availability: Availability? = null
) : Parcelable
