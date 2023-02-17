package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.Celebrity
import com.google.gson.annotations.SerializedName

data class CelebrityDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("user")
    val user: UserDto? = null,
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("is_available")
    val isAvailable: Boolean = true,
    @SerializedName("availability")
    val availability: AvailabilityDto? = null
)

fun CelebrityDto.toCelebrity(): Celebrity {
    return Celebrity(id, price, isAvailable, user?.toUser(), availability?.toAvailability())
}
