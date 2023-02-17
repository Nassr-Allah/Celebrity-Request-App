package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.ChatMessageInfo
import com.google.gson.annotations.SerializedName

data class ChatMessageInfoDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("date_sent")
    val dateSent: String = "",
    @SerializedName("sender")
    val sender: UserDto? = null,
    @SerializedName("recepient")
    val recepient: UserDto? = null,
    @SerializedName("discussion")
    val discussionDto: DiscussionDto? = null,
    @SerializedName("textmessage")
    val textMessageDto: TextMessageDto? = null,
    @SerializedName("voicemessage")
    val voiceMessageDto: VoiceMessageDto? = null,
    @SerializedName("imagemessage")
    val imageMessageDto: ImageMessageDto? = null
)

fun ChatMessageInfoDto.toChatMessageInfo(): ChatMessageInfo {
    return ChatMessageInfo(
        id,
        dateSent,
        discussionDto?.toDiscussion(),
        sender?.toUser(),
        recepient?.toUser(),
        textMessageDto?.toTextMessage(),
        voiceMessageDto?.toVoiceMessage(),
        imageMessageDto?.toImageMessage()
    )
}
