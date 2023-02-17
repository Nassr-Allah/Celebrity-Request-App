package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins

@Composable
fun CustomDropDownMenu(label: String, list: List<Pair<String, String>>, initialValue: Int, onClick: (Int) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedIndex by remember {
        mutableStateOf(initialValue)
    }
    var textValue by remember {
        mutableStateOf(list[initialValue])
    }
    Box(modifier = Modifier.fillMaxWidth(0.85f), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Poppins,
                color = Gray,
                fontSize = dimensionResource(R.dimen.body_medium).value.sp,
                modifier = Modifier.fillMaxWidth(0.95f)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(10))
                    .clickable {
                        expanded = true
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 10.dp, top = 15.dp, bottom = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = list[selectedIndex].first,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = dimensionResource(R.dimen.body_medium).value.sp
                    )
                    Icon(
                        painter = painterResource(R.drawable.down_arrow),
                        contentDescription = null,
                        tint = Orange,
                        modifier = Modifier.size(dimensionResource(R.dimen.drop_down_menu_btn_size))
                    )
                }
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(Color.White)
        ) {
            list.forEachIndexed { index, value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = value.first,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = dimensionResource(R.dimen.body_medium).value.sp
                        )
                    },
                    onClick = {
                        onClick(index)
                        selectedIndex = index
                        expanded = false
                    },

                    )
            }
        }

    }
}