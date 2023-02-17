package com.example.dedicas.feature_request.presentation.requests_list_screen

import android.content.ContentResolver
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
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
import com.example.dedicas.core.data.models.OfferRequest
import com.example.dedicas.core.presentation.navigation.MainAppNavGraph
import com.example.dedicas.core.presentation.ui_components.ListItem
import com.example.dedicas.core.presentation.ui_components.PopupCard
import com.example.dedicas.core.presentation.ui_components.ScreenHeader
import com.example.dedicas.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainAppNavGraph
@Destination
@Composable
fun RequestListScreen(
    navController: NavController,
    viewModel: RequestsListViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    var isCardVisible by remember {
        mutableStateOf(false)
    }
    val cardAlpha = animateFloatAsState(if (isCardVisible) 1f else 0f)
    val backgroundAlpha = animateFloatAsState(if (isCardVisible) 0.2f else 1f)

    val statusList = listOf(
        Pair(stringResource(R.string.all), "all"),
        Pair(stringResource(R.string.accepted), "accepted"),
        Pair(stringResource(R.string.pending), "pending"),
        Pair(stringResource(R.string.refused), "refused"),
        Pair(stringResource(R.string.payment_rejected), "payment rejected"),
        Pair(stringResource(R.string.on_going), "on going"),
        Pair(stringResource(R.string.canceled), "canceled"),
    )

    var isLoading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current.applicationContext
    val contentResolver = context.contentResolver

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        Log.d("RequestsListScreen", state.response.toString())
        if (state.response != null) {
            if (state.response.isSuccessful && state.response.code() == 200) {
                Toast.makeText(context, context.getString(R.string.payment_sent), Toast.LENGTH_SHORT).show()
                isCardVisible = false
            }
        }
        if (!isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .alpha(backgroundAlpha.value)
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(title = stringResource(R.string.requests), enabled = false) {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.height(30.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = LightGray)
            Spacer(modifier = Modifier.height(20.dp))
            FilterRow(list = statusList, viewModel = viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            RequestsList(list = viewModel.filteredRequests.value, viewModel = viewModel) {
                isCardVisible = true
            }
        }
    }
    if (isCardVisible) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PopupCard(
                alpha = cardAlpha.value,
                label = stringResource(R.string.receipt),
                isLoading = isLoading,
                onBackClick = { isCardVisible = false },
                onImagePick = {
                    viewModel.uri.value = it
                }
            ) {
                viewModel.createPayment(contentResolver, context)
            }
        }
    }
}

@Composable
fun RequestsList(
    list: List<OfferRequest>,
    viewModel: RequestsListViewModel,
    onClick: () -> Unit
) {
    LazyColumn {
        items(list) { item ->
            val textColor = when(item.status) {
                "accepted" -> Green
                "on going" -> Yellow
                "pending" -> Yellow
                else -> Red
            }
            val paymentColor = when(item.payment?.status) {
                "confirmed" -> Green
                "updated" -> Yellow
                "pending" -> Yellow
                else -> Red
            }
            val offerLabel = stringResource(R.string.offer)
            val paymentLabel = stringResource(R.string.payment)
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = LightGray)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$offerLabel: ${item.status}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Poppins,
                color = textColor,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "$paymentLabel: ${item.payment?.status}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Poppins,
                color = paymentColor,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            ListItem(
                title = "${item.recepient?.firstName} ${item.recepient?.lastName}",
                subtitle = item.title,
                label = stringResource(R.string.pay)
            ) {
                viewModel.offerId.value = item.id ?: 0
                viewModel.offer.value = item
                Log.d("RequestsListScreen", viewModel.offer.value.toString())
                onClick()
            }
        }
    }
}

@Composable
fun FilterRow(list: List<Pair<String, String>>, viewModel: RequestsListViewModel) {
    var selectedItem by remember {
        mutableStateOf(0)
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(0.85f)
    ) {
        items(list.size) { index ->
            FilterItem(isSelected = selectedItem == index, text = list[index].first) {
                selectedItem = index
                Log.d("RequestsListScreen", list[index].second)
                viewModel.filterRequests(list[index].second)
                Log.d("RequestsListScreen", "${viewModel.filteredRequests.value}")
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun FilterItem(isSelected: Boolean, text: String, onClick: () -> Unit) {
    val backgroundColor = animateColorAsState(if (isSelected) Orange else Color.White)
    val borderColor = animateColorAsState(if (isSelected) Color.Transparent else Gray)
    val textColor = animateColorAsState(if (isSelected) Color.White else Gray)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .border(width = 1.dp, color = borderColor.value, shape = RoundedCornerShape(100))
            .background(backgroundColor.value)
            .clickable {
                onClick()
            }
            .padding(horizontal = 17.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.value,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(R.dimen.body_medium).value.sp
        )
    }
}
