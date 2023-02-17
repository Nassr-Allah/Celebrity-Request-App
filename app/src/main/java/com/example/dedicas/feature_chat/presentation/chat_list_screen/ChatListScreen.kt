package com.example.dedicas.feature_chat.presentation.chat_list_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.R
import com.example.dedicas.core.data.models.Discussion
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.ListItem
import com.example.dedicas.core.presentation.ui_components.ScreenHeader
import com.example.dedicas.destinations.ChatScreenDestination
import com.example.dedicas.ui.theme.LightGray
import com.example.dedicas.ui.theme.Orange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate

@MainAppNavGraph
@Destination
@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val context = LocalContext.current.applicationContext

    var isLoading by remember {
        mutableStateOf(state.isLoading)
    }

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (!isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Orange)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenHeader(title = stringResource(R.string.chat), enabled = false) {
                    navController.popBackStack()
                }
                Spacer(modifier = Modifier.height(30.dp))
                ChatList(
                    list = state.discussions,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ChatList(list: List<Discussion>, navController: NavController) {
    LazyColumn {
        items(list) { item ->
            if (item.messages.isNotEmpty()) {
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = LightGray)
                ListItem(
                    title = "${item.messages[0].recepient?.firstName} ${item.messages[0].recepient?.lastName}",
                    subtitle = "${item.messages[item.messages.size - 1]}",
                    label = stringResource(R.string.browse),
                    isButtonEnabled = false
                ) {
                    navController.navigate(ChatScreenDestination(item))
                }
            }
        }
    }
}
