package com.example.dedicas.feature_authentication.presentation.otp_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.dedicas.destinations.SmsCodeScreenDestination
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun OtpScreen(
    navigator: DestinationsNavigator,
    user: User?,
    viewModel: OtpScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val activity = LocalContext.current as Activity

    var isLoading by remember {
        mutableStateOf(false)
    }

    val phoneKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (!isLoading && state.verificationId.isNotEmpty()) {
            val currentUser = user?.copy(
                phoneNumber = "+213${viewModel.phoneNumber.value}",
                username = "+213${viewModel.phoneNumber.value}"
            )
            navigator.navigate(SmsCodeScreenDestination(currentUser, state.verificationId))
        }
        if (!isLoading && state.error.isNotEmpty()) {
            Toast.makeText(activity.applicationContext, state.error, Toast.LENGTH_SHORT).show()
        }
    }

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
            CustomTextField(
                label = stringResource(R.string.phone_number),
                value = viewModel.phoneNumber.value,
                isError = !viewModel.isPhoneNumberValid.value,
                keyboardOptions = phoneKeyboardOptions,
                leadingIcon = {
                    Text(
                        text = "+213",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = Poppins,
                        fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Orange,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            ) {
                viewModel.phoneNumber.value = it
                Log.d("OtpScreen", viewModel.phoneNumber.value)
                viewModel.isPhoneNumberValid.value = true
            }
            Spacer(modifier = Modifier.height(50.dp))
            CustomButton(label = stringResource(R.string.next)) {
                Log.d("OtpScreen", viewModel.phoneNumber.value)
                if (viewModel.validatePhoneNumber()) {
                    Log.d("OtpScreen", viewModel.phoneNumber.value)
                    //viewModel.verifyPhoneNumber(activity)
                    val currentUser = user?.copy(
                        phoneNumber = "+213${viewModel.phoneNumber.value}",
                        username = "+213${viewModel.phoneNumber.value}"
                    )
                    navigator.navigate(SmsCodeScreenDestination(currentUser, "someid"))
                }
            }
        }
    }
}
