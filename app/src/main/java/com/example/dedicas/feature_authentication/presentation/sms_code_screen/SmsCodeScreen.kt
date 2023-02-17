package com.example.dedicas.feature_authentication.presentation.sms_code_screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.R
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.presentation.navigation.AuthNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.ScreenTitle
import com.example.dedicas.core.presentation.ui_components.SmsCodeField
import com.example.dedicas.destinations.ConfirmationScreenDestination
import com.example.dedicas.feature_authentication.presentation.confirmation_screen.ConfirmationScreen
import com.example.dedicas.ui.theme.DarkGray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@AuthNavGraph
@Destination
@Composable
fun SmsCodeScreen(
    navigator: DestinationsNavigator,
    user: User?,
    verificationId: String,
    viewModel: SmsCodeScreenViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity

    Log.d("SmsCodeScreen", viewModel.state.value.toString())

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
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            ScreenTitle(
                title = stringResource(R.string.otp),
                subtitle = stringResource(R.string.confirm_your_phone)
            )
            Spacer(modifier = Modifier.height(60.dp))
            SmsFields(viewModel)
            Spacer(modifier = Modifier.height(50.dp))
            SmsScreenFooter(navigator, user, verificationId, viewModel)
        }
    }
}

@Composable
fun SmsFields(viewModel: SmsCodeScreenViewModel) {

    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmsCodeField(value = viewModel.digit1.value) {
            viewModel.digit1.value = it
        }
        SmsCodeField(value = viewModel.digit2.value) {
            viewModel.digit2.value = it
        }
        SmsCodeField(value = viewModel.digit3.value) {
            viewModel.digit3.value = it
        }
        SmsCodeField(value = viewModel.digit4.value) {
            viewModel.digit4.value = it
        }
        SmsCodeField(value = viewModel.digit5.value) {
            viewModel.digit5.value = it
        }
        SmsCodeField(value = viewModel.digit6.value, isLast = true) {
            viewModel.digit6.value = it
        }
    }
}

@Composable
fun SmsScreenFooter(
    navigator: DestinationsNavigator,
    user: User?,
    verificationId: String,
    viewModel: SmsCodeScreenViewModel
) {

    val activity = LocalContext.current as Activity
    val state = viewModel.state.value

    val originalText = stringResource(R.string.resend_code)
    var resendCodeText by remember {
        mutableStateOf(originalText)
    }
    var countdown by remember {
        mutableStateOf(60)
    }
    var isRunning by remember {
        mutableStateOf(state.isLoading)
    }

    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            if (countdown == 0) {
                resendCodeText = originalText
                isRunning = false
            } else {
                delay(100L)
                countdown--
                resendCodeText = countdown.toString()
            }
        } else {
            resendCodeText = originalText
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomButton(label = stringResource(R.string.next)) {
            if (viewModel.validateSmsCode(verificationId)) {
                navigator.navigate(ConfirmationScreenDestination(user))
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.code_not_received),
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGray,
                fontFamily = Poppins,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}
