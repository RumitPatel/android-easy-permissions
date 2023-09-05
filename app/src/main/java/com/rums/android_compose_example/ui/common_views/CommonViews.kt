package com.rums.android_compose_example.ui.common_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.rums.android_compose_example.R
import com.rums.android_compose_example.utils.jost_medium

@Composable
fun MyPasswordTextField(
    value: String,
    valueVisibility: Boolean,
    isError: Boolean,
    errorMessage: String,
    placeholder: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        placeholder = { MyText(text = placeholder) },
        label = { MyText(text = label) },
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (valueVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = colorResource(R.color.colorGrayLightAlphaAlpha),
            focusedIndicatorColor = Color.Transparent, //hide the bottom indicator line
            unfocusedIndicatorColor = Color.Transparent //hide the bottom indicator line
        )
    )
    if (isError) {
        MyText(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun MyText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = jost_medium,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textAlign = textAlign
    )
}