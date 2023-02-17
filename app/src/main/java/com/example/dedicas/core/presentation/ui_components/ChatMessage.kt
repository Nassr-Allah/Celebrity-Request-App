package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Poppins

@Composable
fun ChatMessage(message: String, count: Int) {
    val topStartRadius = if (count % 3 == 0) 35 else 0
    val bottomStartRadius = if (count % 3 == 0) 0 else 35
    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (count % 3 == 0) {
            ProfilePic(
                modifier = Modifier.size(dimensionResource(R.dimen.chat_pic_size)),
                img = R.drawable.mark
            )
        } else {
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.chat_pic_size)))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Card(
            shape = RoundedCornerShape(
                topStartPercent = topStartRadius,
                topEndPercent = 100,
                bottomStartPercent = bottomStartRadius,
                bottomEndPercent = 100
            ),
            elevation = CardDefaults.cardElevation(3.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontFamily = Poppins,
                fontSize = dimensionResource(R.dimen.body_small).value.sp,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
        }
    }
}