package com.example.dedicas.core.presentation.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.example.dedicas.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsCodeField(value: String, isLast: Boolean = false, onValueChange: (String) -> Unit) {
    var digit by remember {
        mutableStateOf(value)
    }
    val focusManager = LocalFocusManager.current
    val maxLength = 1
    OutlinedTextField(
        value = digit,
        modifier = Modifier.width(dimensionResource(R.dimen.sms_code_field_width)),
        onValueChange = {
            if (it.length <= maxLength) {
                digit = it
                onValueChange(it)
                if (!isLast && it.length == 1) {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            }
        },
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            cursorColor = Orange,
            containerColor = Color.White,
            focusedBorderColor = Orange,
            unfocusedBorderColor = Gray,
            focusedLabelColor = Orange,
            unfocusedLabelColor = Gray,
            errorBorderColor = Red,
            errorLabelColor = Red
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
    )
}