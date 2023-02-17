package com.example.dedicas.core.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dedicas.NavGraphs
import com.example.dedicas.R
import com.example.dedicas.appCurrentDestinationAsState
import com.example.dedicas.core.data.models.Celebrity
import com.example.dedicas.core.data.models.Discussion
import com.example.dedicas.core.presentation.routes.Screen
import com.example.dedicas.destinations.*
import com.example.dedicas.feature_browsing.presentation.celeb_list_screen.CelebsListScreen
import com.example.dedicas.feature_browsing.presentation.celeb_profile_screen.CelebProfileScreen
import com.example.dedicas.feature_chat.presentation.chat_list_screen.ChatListScreen
import com.example.dedicas.feature_chat.presentation.chat_screen.ChatScreen
import com.example.dedicas.feature_profile.presentation.profile_screen.ProfileScreen
import com.example.dedicas.feature_request.presentation.requests_list_screen.RequestListScreen
import com.example.dedicas.feature_request.presentation.send_request_screen.SendRequestScreen
import com.example.dedicas.startAppDestination
import com.example.dedicas.ui.theme.LightBlue
import com.example.dedicas.ui.theme.LightGray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Purple40
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppNavigation() {
    val navController = rememberNavController()
    val bottomBarItems = mapOf<DirectionDestinationSpec, Int>(
        Pair(CelebsListScreenDestination, R.drawable.ic_people),
        Pair(ChatListScreenDestination, R.drawable.ic_message),
        Pair(RequestListScreenDestination, R.drawable.ic_archive),
        Pair(ProfileScreenDestination, R.drawable.ic_profile),
    )
    var selectedItem by remember {
        mutableStateOf(Screen.CelebListScreen.route)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar (
                containerColor = LightGray,
                contentColor = Color.White,
            )
            {
                val currentDestination: Destination? = navController.appCurrentDestinationAsState().value
                    ?: NavGraphs.mainApp.startAppDestination
                BottomBarDestination.values().forEach { item ->
                    NavigationBarItem(
                        onClick = {
                            navController.navigate(item.direction) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(dimensionResource(R.dimen.back_btn_size))
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(item.label),
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = dimensionResource(R.dimen.body_small).value.sp
                            )
                        },
                        selected = currentDestination == item.direction,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Orange,
                            selectedTextColor = Orange,
                            unselectedIconColor = Color.Black,
                            unselectedTextColor = Color.Black,
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.mainApp,
            modifier = Modifier.padding(it),
            navController = navController
        )
    }
}