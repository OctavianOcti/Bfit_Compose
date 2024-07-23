package com.example.bfit.navdrawerfeatures.goals.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.bfit.R


@Composable
fun SingleChoiceDialogSample(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,  // Add this parameter
    visible: Boolean
) {
    // Ensure selectedOption defaults to the first option when visible
    val initialSelectedOption = if (selectedOption.isEmpty() && options.isNotEmpty()) {
        options.first()
    } else {
        selectedOption
    }

    if (visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(
                    text = title,
                    color = colorResource(id = R.color.blueForDarkGrey)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (option == initialSelectedOption),
                                onClick = { onOptionSelected(option) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color.White,
                                    unselectedColor = Color.White
                                )
                            )
                            Text(
                                text = option,
                                fontSize = 17.sp,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onOptionSelected(initialSelectedOption)
                    onConfirm()  // Call the onConfirm lambda
                    onDismissRequest()
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
            titleContentColor = Color.White,
            textContentColor = Color.White,
            properties = DialogProperties(dismissOnClickOutside = false)
        )
    }
}

