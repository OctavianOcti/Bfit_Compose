package com.example.bfit.authvalidation.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darkGrey)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 25.sp,
            color = colorResource(id = R.color.darkWhite),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            maxLines = 1,
            onValueChange = { email = it },
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=5.dp,end=5.dp, top=0.dp,bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(id = R.string.passwowrd)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=5.dp,end=5.dp, top=0.dp,bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(
            onClick = { /* Handle login */ },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start=5.dp,end=5.dp, top=0.dp,bottom = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        TextButton(onClick = { /* Handle forgot password */ }) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = colorResource(id = R.color.blue_shade),
                textAlign = TextAlign.Center
            )
        }

        TextButton(onClick = { /* Handle register now */ }) {
            Text(
                text = stringResource(id = R.string.click_to_register),
                color = colorResource(id = R.color.blue_shade ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
