package com.example.bfit.authentication.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bfit.R
import com.example.bfit.authentication.presentation.RegistrationFormEvent

@Composable
fun RegisterScreen() {
    val viewModel = viewModel<RegisterViewModel>()
    val state = viewModel.state
    val context = LocalContext.current

    val isAtLeast8Characters by remember(state.password) {
        derivedStateOf { state.password.length >= 8 }
    }
    val hasUppercase by remember(state.password) {
        derivedStateOf { state.password.any { it.isUpperCase() } }
    }
    val hasNumber by remember(state.password) {
        derivedStateOf { state.password.any { it.isDigit() } }
    }
    val hasSymbol by remember(state.password) {
        derivedStateOf { state.password.any { !it.isLetterOrDigit() } }
    }
    val isPasswordValid by remember(isAtLeast8Characters, hasUppercase, hasNumber, state.repeatedPasswordError) {
        derivedStateOf { isAtLeast8Characters && hasUppercase && hasNumber && hasSymbol && state.repeatedPasswordError==null }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is RegisterViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darkGrey)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.register),
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
            },
            label = { Text(text = stringResource(id = R.string.email)) },
            isError = state.emailError != null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textStyle = TextStyle(color = colorResource(id = R.color.darkWhite))
        )
        if (state.emailError != null) {
            Text(
                text = state.emailError,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            label = { Text(text = stringResource(id = R.string.passwowrd)) },
            isError = state.passwordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = colorResource(id = R.color.darkWhite))
        )
        if (state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.repeatedPassword,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
            },
            label = { Text(text = stringResource(id = R.string.confirm_password)) },
            isError = state.repeatedPasswordError != null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            textStyle = TextStyle(color = colorResource(id = R.color.darkWhite))
        )
        if (state.repeatedPasswordError != null) {
            Text(
                text = state.repeatedPasswordError,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.password_should_be),
            color = colorResource(id = R.color.darkWhite),
            modifier = Modifier.fillMaxWidth()
        )

        PasswordCriteria(
            text = stringResource(id = R.string.at_least_8_characters),
            cardColor = if (isAtLeast8Characters) Color.Green else Color.Red
        )
        PasswordCriteria(
            text = stringResource(id = R.string.minimum_one_uppercase),
            cardColor = if (hasUppercase) Color.Green else Color.Red
        )
        PasswordCriteria(
            text = stringResource(id = R.string.minimum_one_number),
            cardColor = if (hasNumber) Color.Green else Color.Red
        )
        PasswordCriteria(
            text = stringResource(id = R.string.minimum_one_symbol),
            cardColor = if (hasSymbol) Color.Green else Color.Red
        )

        // You can add more PasswordCriteria calls for other criteria

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(RegistrationFormEvent.Submit)
            },
            colors = ButtonDefaults.buttonColors(containerColor = if (isPasswordValid) Color.Green else colorResource(id = R.color.gainsboro)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.click_to_login),
            fontSize = 20.sp,
            color = colorResource(id = R.color.blue_shade),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PasswordCriteria(text: String, cardColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(20.dp)
                .padding(start = 4.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Box(
                modifier = Modifier
                    .background(cardColor)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_check_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(16.dp) // Adjust size if necessary
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = colorResource(id = R.color.blueForDarkGrey),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}
