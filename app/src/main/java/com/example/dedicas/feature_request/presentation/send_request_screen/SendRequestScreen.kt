package com.example.dedicas.feature_request.presentation.send_request_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.ui_components.BackButton
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.ScreenTitle
import com.example.dedicas.ui.theme.Orange
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun SendRequestScreen(
    navigator: DestinationsNavigator,
    recepientId: Int,
    viewModel: SendRequestViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val context = LocalContext.current.applicationContext

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (state.response != null) {
            if (state.response.isSuccessful && state.response.code() == 201) {
                Toast.makeText(
                    context,
                    context.getString(R.string.request_sent),
                    Toast.LENGTH_SHORT
                ).show()
                navigator.popBackStack()
            }
        }
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                BackButton {
                    navigator.popBackStack()
                }
                ScreenTitle(
                    title = stringResource(R.string.offer_details),
                    subtitle = stringResource(R.string.describe_your_offer)
                )
                RequestDetails(viewModel)
                CustomButton(label = stringResource(R.string.send)) {
                    viewModel.createRequest(recepientId)
                }
            }
        }
    }
}

@Composable
fun RequestDetails(viewModel: SendRequestViewModel) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CustomTextField(
            label = stringResource(R.string.title),
            value = viewModel.title.value,
            isError = !viewModel.isTitleValid.value
        ) {
            viewModel.title.value = it
            viewModel.isTitleValid.value = true
        }
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            label = stringResource(R.string.description),
            value = viewModel.description.value,
            isError = !viewModel.isDescriptionValid.value,
            modifier = Modifier.fillMaxHeight(0.5f),
            maxLines = 8
        ) {
            viewModel.description.value = it
            viewModel.isDescriptionValid.value = true
        }
    }
}
