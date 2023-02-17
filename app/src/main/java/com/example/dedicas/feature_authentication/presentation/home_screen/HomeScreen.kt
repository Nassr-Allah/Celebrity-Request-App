package com.example.dedicas.feature_authentication.presentation.home_screen

import android.content.Intent
import android.view.Window
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.dedicas.AppActivity
import com.example.dedicas.R
import com.example.dedicas.core.presentation.navigation.AuthNavGraph
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.core.presentation.ui_components.CustomButton
import com.example.dedicas.core.util.Constants
import com.example.dedicas.destinations.LoginScreenDestination
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.w3c.dom.Text


@AuthNavGraph(start = true)
@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current.applicationContext
    val state = viewModel.state.value
    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (state.authToken != null) {
            if (state.authToken.token.isNotEmpty()) {
                val intent = Intent(context, AppActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
        if (!state.isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    if (isLoading) {
        CircularProgressIndicator(color = Orange)
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    shape = RoundedCornerShape(bottomEndPercent = 5, bottomStartPercent = 5),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.55f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.home_bg),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.request_a_gift_from),
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = Poppins,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 45.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensionResource(R.dimen.title_large).value.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.lorem_ipsum),
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = Poppins,
                    color = Color.Gray,
                    maxLines = 3,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp)
            ) {
                CustomButton(label = stringResource(R.string.get_started)) {
                    navigator.navigate(LoginScreenDestination())
                }
            }
        }
    }
}
