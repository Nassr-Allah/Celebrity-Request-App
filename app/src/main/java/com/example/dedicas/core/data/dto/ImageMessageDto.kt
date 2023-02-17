package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.ImageMessage
import com.google.gson.annotations.SerializedName

data class ImageMessageDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: UserDto? = null,
    @SerializedName("date_sent")
    val dateSent: String = "",
    @SerializedName("image")
    val image: String
)

fun ImageMessageDto.toImageMessage(): ImageMessage {
    return ImageMessage(id, image)
}
