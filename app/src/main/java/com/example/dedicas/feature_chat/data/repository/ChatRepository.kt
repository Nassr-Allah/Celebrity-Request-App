package com.example.dedicas.feature_chat.data.repository

import com.example.dedicas.core.data.dto.ChatMessageInfoDto
import com.example.dedicas.core.data.dto.DiscussionDto

interface ChatRepository {

    suspend fun getDiscussionMessages(token: String, discussionId: Int): List<ChatMessageInfoDto>

    suspend fun getClientDiscussions(token: String): List<DiscussionDto>

}