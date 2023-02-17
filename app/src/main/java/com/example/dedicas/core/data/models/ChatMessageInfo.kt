package com.example.dedicas.core.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatMessageInfo(
    val id: Int? = null,
    val dateSent: String = "",
    val discussion: Discussion? = null,
    val sender: User? = null,
    val recepient: User? = null,
    val textMessage: TextMessage? = null,
    val voiceMessage: VoiceMessage? = null,
    val imageMessage: ImageMessage? = null
) : Parcelable
