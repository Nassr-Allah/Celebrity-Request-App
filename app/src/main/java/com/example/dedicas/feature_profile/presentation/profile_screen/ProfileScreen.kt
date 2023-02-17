package com.example.dedicas.feature_profile.presentation.profile_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.dedicas.LocalMutableContext
import com.example.dedicas.MyApplication
import com.example.dedicas.R
import com.example.dedicas.core.data.models.Client
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.ui_components.CustomDropDownMenu
import com.example.dedicas.core.presentation.ui_components.CustomTextField
import com.example.dedicas.core.presentation.ui_components.TitleAndSubtitle
import com.example.dedicas.core.util.LanguageHelper
import com.example.dedicas.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current as Activity

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (!isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
        if (state.logoutUserResponse != null) {
            if (state.logoutUserResponse.isSuccessful && state.logoutUserResponse.code() == 204) {
                context.finish()
            }
        }
        if (state.updateUserResponse != null) {
            if (state.updateUserResponse.isSuccessful) {
                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (isLoading) {
        CircularProgressIndicator(color = Orange)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 25.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    ProfileScreenHeader(viewModel.currentClient.value, viewModel)
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(thickness = 1.dp, color = LightGray, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(15.dp))
                    StatsBoxes(viewModel)
                }
                Spacer(modifier = Modifier.height(15.dp))
                ProfileDetails(viewModel.currentClient.value, viewModel)
                Spacer(modifier = Modifier.height(15.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(thickness = 1.dp, color = LightGray, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(15.dp))
                    PrivacyPolicyRow()
                    Spacer(modifier = Modifier.height(18.dp))
                    OutlinedButton(
                        onClick = {
                            viewModel.logoutUser()
                        },
                        modifier = Modifier.fillMaxWidth(0.85f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Red,
                            containerColor = CustomWhite
                        ),
                        border = BorderStroke(width = 1.dp, color = Red),
                        shape = RoundedCornerShape(10)
                    ) {
                        Text(
                            text = stringResource(R.string.log_out),
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = Poppins,
                            fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreenHeader(client: Client, viewModel: ProfileScreenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Spacer(modifier = Modifier.width(15.dp))
            TitleAndSubtitle(
                title = "${client.user?.firstName} ${client.user?.lastName}",
                subtitle = "${client.user?.phoneNumber}"
            )
        }
        if (viewModel.isInfoChanged.value) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = Green,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            viewModel.updateUser()
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(R.drawable.ic_cancel),
                    contentDescription = null,
                    tint = Red,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            viewModel.refreshInfo()
                            viewModel.isInfoChanged.value = false
                        }
                )
            }
        }
    }
}

@Composable
fun NextArrowButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .clip(CircleShape)
            .border(width = 1.dp, shape = CircleShape, color = Orange)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_next),
            contentDescription = null,
            tint = Orange,
            modifier = Modifier.size(dimensionResource(R.dimen.next_arrow_size))
        )
    }
}

@Composable
fun StatsBoxes(viewModel: ProfileScreenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        StatisticBox(label = stringResource(R.string.sent), number = "${viewModel.sentRequests.value}")
        StatisticBox(label = stringResource(R.string.pending), number = "${viewModel.pendingRequests.value}")
        StatisticBox(label = stringResource(R.string.accepted), number = "${viewModel.acceptedRequests.value}")
    }
}

@Composable
fun StatisticBox(label: String, number: String) {
    Box(
        modifier = Modifier
            .width(dimensionResource(R.dimen.stat_box_width))
            .border(width = 1.dp, color = Orange, shape = RoundedCornerShape(10))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = number,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Poppins,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = dimensionResource(R.dimen.body_large).value.sp
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = Poppins,
                color = DarkGray,
                fontWeight = FontWeight.Normal,
                fontSize = dimensionResource(R.dimen.body_small).value.sp
            )
        }
    }
}

@Composable
fun PrivacyPolicyRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleAndSubtitle(
            title = stringResource(R.string.privacy_policy),
            subtitle = stringResource(R.string.protect_your_privacy)
        )
        NextArrowButton {
            //TODO: implement privacy policy next Button
        }
    }
}

@Composable
fun ProfileDetails(client: Client, viewModel: ProfileScreenViewModel) {
    val languages = listOf(
        Pair(stringResource(R.string.english), "en"),
        Pair(stringResource(R.string.french), "fr"),
        Pair(stringResource(R.string.english), "en"),
    )
    val context = LocalContext.current.applicationContext
    val language = LanguageHelper.getUserLanguage(context)
    val mutableContext = LocalMutableContext.current
    val activity = LocalContext.current as Activity

    val selectedIndex = if (language == "en") 0 else 1

    var index by remember {
        mutableStateOf(selectedIndex)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextField(label = stringResource(R.string.first_name), value = viewModel.firstName.value) {
            viewModel.firstName.value = it
            viewModel.checkInfoChange()
        }
        Spacer(modifier = Modifier.height(5.dp))
        CustomTextField(label = stringResource(R.string.last_name), value = viewModel.lastName.value) {
            viewModel.lastName.value = it
            viewModel.checkInfoChange()
        }
        Spacer(modifier = Modifier.height(5.dp))
        CustomTextField(label = stringResource(R.string.email), value = viewModel.email.value) {
            viewModel.email.value = it
            viewModel.checkInfoChange()
        }
        Spacer(modifier = Modifier.height(5.dp))
        CustomDropDownMenu(label = stringResource(R.string.language), list = languages, initialValue = index) {
            index = it
            Log.d("ProfileScreen", languages[index].toString())
            LanguageHelper.storeLanguage(mutableContext.value, languages[index].second)
            LanguageHelper.updateLanguage(mutableContext.value, languages[index].second)
            activity.finish()
            activity.startActivity(activity.intent)
            Log.d("ProfileScreen", LanguageHelper.getUserLanguage(context))
        }
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
fun ProfileScreenPreview() {
    DedicasTheme {
        val navController = rememberNavController()
        ProfileScreen(navController = navController)
    }
}
