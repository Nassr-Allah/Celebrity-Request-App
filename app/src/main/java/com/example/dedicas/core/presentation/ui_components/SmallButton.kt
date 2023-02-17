package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins

@Composable
fun SmallButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(100),
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.White
        ),
        modifier = Modifier.height(35.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontFamily = Poppins,
            fontSize = dimensionResource(R.dimen.body_small).value.sp
        )
    }
}