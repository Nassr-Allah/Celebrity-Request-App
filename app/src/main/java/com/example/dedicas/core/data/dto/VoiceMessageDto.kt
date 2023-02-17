package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.VoiceMessage
import com.google.gson.annotations.SerializedName

data class VoiceMessageDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: UserDto? = null,
    @SerializedName("date_sent")
    val dateSent: String = "",
    @SerializedName("audio")
    val audio: String = ""
)

fun VoiceMessageDto.toVoiceMessage(): VoiceMessage {
    return VoiceMessage(id, audio)
}
