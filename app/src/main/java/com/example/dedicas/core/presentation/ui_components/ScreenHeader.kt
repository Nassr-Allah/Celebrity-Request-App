package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Poppins

@Composable
fun ScreenHeader(title: String, enabled: Boolean = true, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
            fontSize = dimensionResource(R.dimen.title_small).value.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold
        )
        if (enabled) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton {
                    onClick()
                }
            }
        }
    }
}