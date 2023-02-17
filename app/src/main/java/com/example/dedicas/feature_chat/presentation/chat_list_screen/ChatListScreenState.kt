package com.example.dedicas.feature_chat.presentation.chat_list_screen

import com.example.dedicas.core.data.models.ChatMessageInfo
import com.example.dedicas.core.data.models.Discussion

data class ChatListScreenState(
    val isLoading: Boolean = false,
    val discussions: List<Discussion> = emptyList(),
    val error: String = ""
)
