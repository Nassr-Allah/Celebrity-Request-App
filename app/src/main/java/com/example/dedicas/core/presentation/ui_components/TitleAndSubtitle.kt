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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Poppins

@Composable
fun TitleAndSubtitle(
    title: String,
    subtitle: String,
    textAlign: TextAlign? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(horizontalAlignment = horizontalAlignment) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(R.dimen.body_medium).value.sp,
            textAlign = textAlign
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp,
            textAlign = textAlign
        )
    }
}