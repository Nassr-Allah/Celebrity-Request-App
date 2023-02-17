package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Poppins

@Composable
fun ScreenTitle(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.screen_title).value.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            fontSize = dimensionResource(R.dimen.body_medium).value.sp,
            fontFamily = Poppins
        )
    }
}

@Preview
@Composable
fun ScreenTitlePreview() {
    ScreenTitle(title = "Hello World!", subtitle = "this is a greeting")
}