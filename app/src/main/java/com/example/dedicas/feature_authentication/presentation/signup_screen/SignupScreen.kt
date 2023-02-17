package com.example.dedicas.feature_authentication.presentation.signup_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.R
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.presentation.navigation.AuthNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.ScreenTitle
import com.example.dedicas.destinations.OtpScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun SignupScreen(
    navigator: DestinationsNavigator,
    viewModel: SignupScreenViewModel = SignupScreenViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            ScreenTitle(
                title = stringResource(R.string.new_account),
                subtitle = stringResource(R.string.create_new_account)
            )
            SignupForm(viewModel)
            CustomButton(label = stringResource(R.string.next)) {
                if (viewModel.validateInput()) {
                    val user = User(
                        firstName = viewModel.firstName.value,
                        lastName = viewModel.lastName.value,
                        email = viewModel.email.value
                    )
                    navigator.navigate(OtpScreenDestination(user))
                }
            }
        }
    }
}

@Composable
fun SignupForm(viewModel: SignupScreenViewModel) {

    val emailKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomTextField(
            label = stringResource(R.string.first_name),
            value = viewModel.firstName.value,
            isError = !viewModel.isFirstNameValid.value
        ) {
            viewModel.isFirstNameValid.value = true
            viewModel.firstName.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            label = stringResource(R.string.last_name),
            value = viewModel.lastName.value,
            isError = !viewModel.isLastNameValid.value
        ) {
            viewModel.isLastNameValid.value = true
            viewModel.lastName.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(
            label = stringResource(R.string.email),
            value = viewModel.email.value,
            isError = !viewModel.isEmailValid.value,
            keyboardOptions = emailKeyboardOptions
        ) {
            viewModel.isEmailValid.value = true
            viewModel.email.value = it
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}
