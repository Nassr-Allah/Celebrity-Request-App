package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dedicas.R

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    label: String,
    isButtonEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                if (!isButtonEnabled) {
                    onClick()
                }
            }
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(15.dp))
            TitleAndSubtitle(title = title, subtitle = subtitle)
        }
        if (isButtonEnabled) {
            SmallButton(label = label) {
                onClick()
            }
        }
    }
}
