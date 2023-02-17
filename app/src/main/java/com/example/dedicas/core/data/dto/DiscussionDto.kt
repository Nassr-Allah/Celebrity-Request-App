package com.example.dedicas.core.data.dto

import com.example.dedicas.core.data.models.Discussion
import com.example.dedicas.core.data.models.User
import com.google.gson.annotations.SerializedName

data class DiscussionDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("members")
    val members: List<UserDto> = emptyList(),
    @SerializedName("messages")
    val messages: List<ChatMessageInfoDto> = emptyList()
)

fun DiscussionDto.toDiscussion(): Discussion {
    val membersList = members.map { it.toUser() }
    val messagesList = messages.map { it.toChatMessageInfo() }
    return Discussion(id, membersList, messagesList)
}
