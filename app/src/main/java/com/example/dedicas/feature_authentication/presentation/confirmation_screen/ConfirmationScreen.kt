package com.example.dedicas.feature_authentication.presentation.confirmation_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.AppActivity
import com.example.dedicas.MainActivity
import com.example.dedicas.R
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.data.models.User
import com.example.dedicas.core.presentation.navigation.AuthNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.ScreenTitle
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun ConfirmationScreen(
    viewModel: ConfirmationScreenViewModel = hiltViewModel(),
    user: User?
) {
    val phoneKeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    val context = LocalContext.current.applicationContext
    val state = viewModel.state.value

    viewModel.user.value = user ?: User()

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (state.response != null) {
            if (state.response.isSuccessful && state.response.code() == 201) {
                val intent = Intent(context, AppActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } else if (state.response.code() != 201 && state.response.message().isEmpty()) {
                Toast.makeText(context, "Unexpected Server Error", Toast.LENGTH_SHORT).show()
            } else if (!state.response.isSuccessful) {
                Toast.makeText(context, "Unexpected Server Error", Toast.LENGTH_SHORT).show()
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
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                ScreenTitle(
                    title = stringResource(R.string.last_step),
                    subtitle = stringResource(R.string.input_your_ccp)
                )
                Spacer(modifier = Modifier.height(60.dp))
                CustomTextField(
                    label = stringResource(R.string.password),
                    value = viewModel.password.value,
                    isError = !viewModel.isPasswordMatch.value,
                    keyboardOptions = phoneKeyboardOptions,
                    visualTransformation = PasswordVisualTransformation()
                ) {
                    viewModel.isPasswordMatch.value = true
                    viewModel.password.value = it
                }
                Spacer(modifier = Modifier.height(20.dp))
                CustomTextField(
                    label = stringResource(R.string.confirm_password),
                    value = viewModel.confirmedPassword.value,
                    isError = !viewModel.isPasswordMatch.value,
                    keyboardOptions = phoneKeyboardOptions,
                    visualTransformation = PasswordVisualTransformation()
                ) {
                    viewModel.isPasswordMatch.value = true
                    viewModel.confirmedPassword.value = it
                }
                Spacer(modifier = Modifier.height(50.dp))
                CustomButton(label = stringResource(R.string.next)) {
                    if (viewModel.validatePassword()) {
                        viewModel.user.value =
                            user?.copy(password = viewModel.password.value) ?: User()
                        viewModel.createClient()
                    }
                }
            }
        }
    }
}
