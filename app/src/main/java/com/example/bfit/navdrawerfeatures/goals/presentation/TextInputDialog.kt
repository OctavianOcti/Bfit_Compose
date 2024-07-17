package com.example.bfit.navdrawerfeatures.goals.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.bfit.R


@Composable
fun TextInputDialog(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    visible: Boolean,
    validate: (String) -> Boolean,
    validationMessage: String
) {
    var isValid by remember { mutableStateOf(true) }

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title, color = colorResource(id = R.color.blueForDarkGrey)) },
            text = {
                Column {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = text,
                        onValueChange = {
                            onTextChange(it)
                            isValid = validate(it)
                        },
                        maxLines = 1,
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = colorResource(id = R.color.darkGrey),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = colorResource(id = R.color.blueForDarkGrey),
                            focusedIndicatorColor = colorResource(id = R.color.blueForDarkGrey),
                            unfocusedContainerColor = colorResource(id = R.color.darkGrey),
                            unfocusedIndicatorColor = colorResource(id = R.color.blueForDarkGrey)
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                    )
                    if (!isValid) {
                        Text(
                            text = validationMessage,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (validate(text)) {
                        onConfirm()
                        onDismissRequest()
                    } else {
                        isValid = false
                    }
                }) {
                    Text(text = "OK", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel", color = Color.White)
                }
            },
            containerColor = colorResource(id = R.color.darkGrey),
            textContentColor = Color.Red,
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}


