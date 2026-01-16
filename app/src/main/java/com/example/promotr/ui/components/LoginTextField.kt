package com.example.promotr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.promotr.ui.theme.LoginIconGray
import com.example.promotr.ui.theme.LoginInputBg
import com.example.promotr.ui.theme.LoginPrimary
import com.example.promotr.ui.theme.LoginTextPrimary
import com.example.promotr.ui.theme.LoginTextTertiary

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = LoginTextTertiary) },
        readOnly = readOnly,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = LoginIconGray
                )
            }
        },
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = LoginInputBg,
            disabledContainerColor = LoginInputBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = LoginTextPrimary,
            unfocusedTextColor = LoginTextPrimary,
            focusedLabelColor = LoginTextPrimary,
            unfocusedLabelColor = LoginTextPrimary,
            focusedPlaceholderColor = LoginTextTertiary,
            unfocusedPlaceholderColor = LoginTextTertiary,
            focusedLeadingIconColor = LoginIconGray,
            unfocusedLeadingIconColor = LoginIconGray
        ),
        shape = RoundedCornerShape(12.dp)
    )
}
