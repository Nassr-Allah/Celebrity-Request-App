package com.example.dedicas.feature_chat.presentation.chat_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dedicas.core.data.models.ChatMessageInfo
import com.example.dedicas.core.data.models.Discussion
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.ui_components.ChatMessage
import com.example.dedicas.core.presentation.ui_components.ScreenHeader
import com.example.dedicas.ui.theme.LightBlue
import com.example.dedicas.ui.theme.LightGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun ChatScreen(navigator: DestinationsNavigator, discussion: Discussion?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxHeight(0.13f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScreenHeader(title = "${discussion?.messages?.get(0)?.recepient?.firstName}" +
                        " ${discussion?.messages?.get(0)?.recepient?.firstName}") {
                    navigator.popBackStack()
                }
            }
            Divider(thickness = 1.dp, color = LightGray)
            Spacer(modifier = Modifier.height(25.dp))
            ChatMessagesList(messages = discussion?.messages ?: emptyList())
        }
    }
}

@Composable
fun ChatMessagesList(messages: List<ChatMessageInfo>) {
    LazyColumn {
        items(messages.size) { index ->
            if (messages[index].textMessage != null) {
                ChatMessage(message = "${messages[index].textMessage?.message}", count = index)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

