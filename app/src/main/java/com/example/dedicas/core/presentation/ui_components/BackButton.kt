package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButton(onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(15),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Orange
        ),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.back_arrow),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.ten_spacer))
                .size(dimensionResource(R.dimen.back_btn_size))
        )
    }
}