package com.example.dedicas.feature_authentication.presentation.login_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.AppActivity
import com.example.dedicas.R
import com.example.dedicas.core.presentation.navigation.AuthNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.BackButton
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.ScreenTitle
import com.example.dedicas.destinations.SignupScreenDestination
import com.example.dedicas.ui.theme.DarkGray
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 25.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            ScreenContent(navigator)
            LoginScreenFooter(navigator = navigator)
        }
    }
}

@Composable
fun ScreenContent(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.Start
        ) {
            BackButton {
                navigator.popBackStack()
            }
        }
        Spacer(modifier = Modifier.height(45.dp))
        ScreenTitle(
            title = stringResource(R.string.hello_again),
            subtitle = stringResource(R.string.sign_in_to_your_account)
        )
        Spacer(modifier = Modifier.height(45.dp))
        LoginForm(navigator = navigator)
    }
}

@Composable
fun LoginForm(
    navigator: DestinationsNavigator,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    var isVisible by remember {
        mutableStateOf(false)
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    val iconColor = animateColorAsState(if (isVisible) Orange else Gray)

    val visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation()
    val passwordKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    val phoneKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (state.response != null) {
            if (state.response.isSuccessful && state.response.body() != null) {
                val intent = Intent(context, AppActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        if (!state.isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomTextField(
            label = stringResource(R.string.phone_number),
            value = viewModel.phoneNumber.value,
            isError = !viewModel.isPhoneNumberValid.value,
            keyboardOptions = phoneKeyboardOptions,
        ) {
            viewModel.phoneNumber.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            label = stringResource(R.string.password),
            value = viewModel.password.value,
            keyboardOptions = passwordKeyboardOptions,
            visualTransformation = visualTransformation,
            isError = !viewModel.isPasswordValid.value,
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = null,
                    tint = iconColor.value,
                    modifier = Modifier.clickable {
                        isVisible = !isVisible
                    }
                )
            },
        ) {
            viewModel.password.value = it
        }
        Spacer(modifier = Modifier.height(7.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.bodyMedium,
                color = Orange,
                fontFamily = Poppins,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    //navigator.navigate(OtpScreenDestination())
                }
            )
        }
        Spacer(modifier = Modifier.height(45.dp))
        CustomButton(label = stringResource(R.string.sign_in), isLoading = isLoading) {
            if (viewModel.validateInput()) {
                viewModel.loginUser()
            }
        }
    }
}

@Composable
fun LoginScreenFooter(navigator: DestinationsNavigator) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.dont_have_account),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGray,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_medium).value.sp
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.sign_up),
            style = MaterialTheme.typography.bodyMedium,
            color = Orange,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_medium).value.sp,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.clickable {
                navigator.navigate(SignupScreenDestination())
            }
        )
    }
}
