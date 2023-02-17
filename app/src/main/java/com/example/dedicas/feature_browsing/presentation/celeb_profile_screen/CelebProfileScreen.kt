package com.example.dedicas.feature_browsing.presentation.celeb_profile_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dedicas.R
import com.example.dedicas.core.data.models.Celebrity
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.presentation.ui_components.ScreenHeader
import com.example.dedicas.core.presentation.ui_components.TitleAndSubtitle
import com.example.dedicas.destinations.SendRequestScreenDestination
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.LightGray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun CelebProfileScreen(
    navigator: DestinationsNavigator,
    celebrity: Celebrity?
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                ScreenHeader(title = stringResource(R.string.celebrity_profile)) {
                    navigator.popBackStack()
                }
                Spacer(modifier = Modifier.height(25.dp))
                Divider(thickness = 1.dp, color = LightGray, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(45.dp))
                ProfileHeader(
                    name = "${celebrity?.user?.firstName} ${celebrity?.user?.lastName}",
                    profession = celebrity?.user?.email ?: ""
                )
            }
            Box(modifier = Modifier.fillMaxHeight(0.8f), contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    WorkingTime(
                        startDay = "${celebrity?.availability?.startDay}",
                        endDay = "${celebrity?.availability?.endDay}",
                        startHour = "${celebrity?.availability?.startHour}",
                        endHour = "${celebrity?.availability?.endDay}"
                    )
                    PaymentInfo(celebrity = celebrity)
                    PriceBox(price = "${celebrity?.price}")
                    CustomButton(label = stringResource(R.string.send_request)) {
                        navigator.navigate(SendRequestScreenDestination(celebrity?.id ?: 0))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String, profession: String) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TitleAndSubtitle(
            title = name,
            subtitle = profession,
            textAlign = TextAlign.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }
}

@Composable
fun WorkingTime(startDay: String, endDay: String, startHour: String, endHour: String) {
    val workDays = stringResource(R.string.work_days)
    val workHours = stringResource(R.string.work_hours)
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        WorkTimeDetails(start = startDay, end = endDay, label = workDays)
        Spacer(modifier = Modifier.height(5.dp))
        WorkTimeDetails(start = startHour, end = endHour, label = workHours)
    }
}

@Composable
fun WorkTimeDetails(start: String, end: String, label: String) {
    val to = stringResource(R.string.to)
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 25.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = "$start ",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Orange,
            maxLines = 2,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = "$to ",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Text(
            text = end,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp,
            fontWeight = FontWeight.Medium,
            color = Orange
        )
    }
}

@Composable
fun PaymentInfo(celebrity: Celebrity?) {
    Column(modifier = Modifier.fillMaxWidth(0.85f), horizontalAlignment = Alignment.Start) {
        Text(
            text = "RIP: ${celebrity?.user?.paymentDetails?.rip}",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_large).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "CCP: ${celebrity?.user?.paymentDetails?.ccp}",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_large).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Address: ${celebrity?.user?.paymentDetails?.address}",
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_large).value.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Composable
fun PriceBox(price: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .border(width = 1.dp, color = LightGray, shape = RoundedCornerShape(10))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$price DA",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = Poppins,
                color = Orange,
                fontWeight = FontWeight.SemiBold,
                fontSize = dimensionResource(R.dimen.body_large).value.sp
            )
            Text(
                text = stringResource(R.string.price),
                style = MaterialTheme.typography.bodySmall,
                fontFamily = Poppins,
                color = Gray,
                fontWeight = FontWeight.Normal,
                fontSize = dimensionResource(R.dimen.body_small).value.sp
            )
        }
    }
}
