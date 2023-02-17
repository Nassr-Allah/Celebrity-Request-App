package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.Availability
import com.google.gson.annotations.SerializedName

data class AvailabilityDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("start_day")
    val startDay: String = "",
    @SerializedName("end_day")
    val endDay: String = "",
    @SerializedName("start_hour")
    val startHour: String = "",
    @SerializedName("end_hour")
    val endHour: String
)

fun AvailabilityDto.toAvailability(): Availability {
    return Availability(id, startDay, endDay, startHour, endHour)
}
