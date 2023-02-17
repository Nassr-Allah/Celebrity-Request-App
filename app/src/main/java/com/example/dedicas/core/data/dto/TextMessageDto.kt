package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.TextMessage
import com.google.gson.annotations.SerializedName

data class TextMessageDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: UserDto? = null,
    @SerializedName("date_sent")
    val dateSent: String = "",
    @SerializedName("text_message")
    val message: String = ""
)

fun TextMessageDto.toTextMessage(): TextMessage {
    return TextMessage(id, message)
}
