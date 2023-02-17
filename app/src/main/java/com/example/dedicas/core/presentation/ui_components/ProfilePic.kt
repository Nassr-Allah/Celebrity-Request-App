package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun ProfilePic(modifier: Modifier, img: Int) {
    Card(
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(img),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}