package com.example.dedicas.core.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.dedicas.R
import com.example.dedicas.destinations.CelebsListScreenDestination
import com.example.dedicas.destinations.ChatListScreenDestination
import com.example.dedicas.destinations.ProfileScreenDestination
import com.example.dedicas.destinations.RequestListScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: Int,
    @StringRes val label: Int
) {

    Celebs(CelebsListScreenDestination, R.drawable.ic_people, R.string.celebrities),
    ChatList(ChatListScreenDestination, R.drawable.ic_message, R.string.chat),
    RequestsList(RequestListScreenDestination, R.drawable.ic_archive, R.string.requests),
    Profile(ProfileScreenDestination, R.drawable.ic_profile, R.string.profile),

}