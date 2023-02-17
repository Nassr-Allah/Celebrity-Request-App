package com.example.dedicas.feature_chat.data.repository

import com.example.dedicas.core.data.data_source.remote.RestApi
import com.example.dedicas.core.data.dto.ChatMessageInfoDto
import com.example.dedicas.core.data.dto.DiscussionDto

class ChatRepositoryImpl(val api: RestApi) : ChatRepository {

    override suspend fun getDiscussionMessages(
        token: String,
        discussionId: Int
    ): List<ChatMessageInfoDto> {
        return api.getDiscussionMessages("Token $token", discussionId)
    }

    override suspend fun getClientDiscussions(token: String): List<DiscussionDto> {
        return api.getClientDiscussions("Token $token")
    }
}