package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins

@Composable
fun CustomButton(
    label: String,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(0.85f),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(
            containerColor = Orange,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray
        ),
        enabled = isEnabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 5.dp),
                fontFamily = Poppins,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp
            )
        }
    }
}
