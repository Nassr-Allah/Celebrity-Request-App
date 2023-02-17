package com.example.dedicas.feature_browsing.presentation.celeb_list_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import com.example.dedicas.core.presentation.ui_components.BackButton
import com.example.dedicas.core.presentation.ui_components.ListItem
import com.example.dedicas.core.presentation.ui_components.ScreenHeader
import com.example.dedicas.destinations.CelebProfileScreenDestination
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.LightGray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate

@MainAppNavGraph(start = true)
@Destination(start = true)
@Composable
fun CelebsListScreen(
    navController: NavController,
    viewModel: CelebsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    var isLoading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current.applicationContext

    LaunchedEffect(key1 = state) {
        isLoading = state.isLoading
        if (!isLoading && state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Orange)
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenHeader(title = stringResource(R.string.celebrities), enabled = false) {
                    navController.popBackStack()
                }
                Spacer(modifier = Modifier.height(30.dp))
                CelebList(
                    list = state.celebrities
                ) {
                    navController.navigate(CelebProfileScreenDestination(it))
                }
            }
        }
    }
}

@Composable
fun CelebList(list: List<Celebrity>, onClick: (Celebrity) -> Unit) {
    LazyColumn {
        items(list) { item ->
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = LightGray)
            ListItem(
                title = "${item.user?.firstName} ${item.user?.lastName}",
                subtitle = "${item.user?.email}",
                label = stringResource(R.string.browse)
            ) {
                onClick(item)
            }
        }
    }
}
