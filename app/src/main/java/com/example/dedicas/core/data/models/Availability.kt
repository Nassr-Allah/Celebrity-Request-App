package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Availability(
    val id: Int? = null,
    val startDay: String = "",
    val endDay: String = "",
    val startHour: String = "",
    val endHour: String = ""
) : Parcelable
