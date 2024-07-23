package com.example.bfit.navdrawerfeatures.common.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.bfit.R

//@Composable
//fun AlertDialogWarning (
//    title: String,
//    onDismissRequest: () -> Unit,
//    onConfirm: () -> Unit,
//    visible: Boolean,
//    alertDialogType: String
//){
//    if(visible){
//        AlertDialog(
//            onDismissRequest = onDismissRequest,
//          //  title = { Text(text = title, color = colorResource(id = R.color.blueForDarkGrey)) },
//            title = {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    // Warning icon
//                   Icon(
//                       imageVector = Icons.Default.Warning ,
//                       contentDescription = "",
//                       modifier = Modifier.size(48.dp).fillMaxWidth()
//
//                   )
//                    // Title text
//                    Text(
//                        text = title,
//                        textAlign = TextAlign.Start,
//                        color = colorResource(id = R.color.blueForDarkGrey),
//                        modifier = Modifier.padding(start = 8.dp)
//                    )
//                }
//            },
//
//            confirmButton = {
//                TextButton(onClick = {
//                    onConfirm()  // Call the onConfirm lambda
//                    onDismissRequest()
//                }) {
//                    Text(text = "OK", color = Color.White)
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = onDismissRequest) {
//                    Text(text = "Cancel", color = Color.White)
//                }
//            },
//            containerColor = colorResource(id = R.color.darkGrey),
//            titleContentColor = Color.White,
//            textContentColor = Color.White,
//            properties = DialogProperties(dismissOnClickOutside = false),
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}

@Composable
fun AlertDialogWarning (
    title: String,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    visible: Boolean,
    showCancelButton: Boolean,
    alertMessage: String? = null
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "",
                        modifier = Modifier.size(48.dp).fillMaxWidth()
                    )
                    Text(
                        text = title,
                        textAlign = TextAlign.Start,
                        color = colorResource(id = R.color.blueForDarkGrey),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            },
            text = {
                if (alertMessage != null) {
                    Text(
                        text = alertMessage,
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)

                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                   if(showCancelButton) {
                       onConfirm()
                       onDismissRequest()
                   }
                    else onDismissRequest()
                }) {
                    Text(text = "OK", color = Color.White)
                }
            },
            dismissButton = {
                if (showCancelButton) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Cancel", color = Color.White)
                    }
                }
            },
            containerColor = colorResource(id = R.color.darkGrey),
            titleContentColor = Color.White,
            textContentColor = Color.White,
            properties = DialogProperties(dismissOnClickOutside = false),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

