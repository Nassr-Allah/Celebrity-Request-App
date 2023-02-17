package com.example.dedicas.core.presentation.ui_components

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dedicas.R
import com.example.dedicas.ui.theme.Gray
import com.example.dedicas.ui.theme.Orange
import com.example.dedicas.ui.theme.Poppins
import com.example.dedicas.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    enabled: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {

    var text by remember {
        mutableStateOf(value)
    }
    val error by remember {
        mutableStateOf(isError)
    }
    OutlinedTextField(
        value = value,
        modifier = modifier
            .fillMaxWidth(0.85f)
            .height(dimensionResource(R.dimen.text_field_height)),
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = Poppins
            )
        },
        shape = RoundedCornerShape(7.dp),
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
        maxLines = maxLines,
        isError = error,
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            if (leadingIcon != null) {
                leadingIcon()
            }
        },
        trailingIcon = {
            if (trailingIcon != null) {
                trailingIcon()
            }
        },
        placeholder = {
            if (placeholder != null) {
                placeholder()
            }
        },
        enabled = enabled,
        visualTransformation = visualTransformation,
        textStyle = TextStyle(fontSize = dimensionResource(R.dimen.body_medium).value.sp)
    )
}
