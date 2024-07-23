package com.example.bfit.authentication.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.authentication.presentation.components.CustomOutlinedTextField
import com.example.bfit.authentication.presentation.register.RegisterViewModel
import com.example.bfit.authentication.presentation.register.RegistrationFormEvent

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit = {},
    navigateToMain: () -> Unit = {}
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state = viewModel.state
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsState()

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
    val isPasswordValid by remember(
        isAtLeast8Characters,
        hasUppercase,
        hasNumber,
        state.passwordError
    ) {
        derivedStateOf { isAtLeast8Characters && hasUppercase && hasNumber && hasSymbol && state.passwordError == null  }
    }
    val isValidInputData by remember(
        isPasswordValid,
        state.email,
        state.emailError
    ){
        derivedStateOf { isPasswordValid && state.email != "" && state.emailError == null}
    }


    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    viewModel.onEvent(LoginFormEvent.Login)
                    val authState = viewModel.loginState.value
                    when {
                        authState.isSuccess -> {
//                            Log.d("LoginScreen", "Logged successfully")
//                            Toast.makeText(
//                                context,
//                                "Login successful",
//                                Toast.LENGTH_LONG
//                            ).show()
                           // navigateToMain()
                        }

                        authState.isError != null -> {
                            Toast.makeText(
                                context,
                                "Login failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is LoginViewModel.ValidationEvent.Success -> {
                    viewModel.onEvent(LoginFormEvent.Login)
                    // Assuming registerState is part of AuthState, handle its states
                }
            }
        }
    }
    LaunchedEffect(key1 = loginState) {
        when {
            loginState.isLoading -> {
            }

            loginState.isSuccess -> {
                Toast.makeText(
                    context,
                    "Login successfully",
                    Toast.LENGTH_LONG
                ).show()
                navigateToMain()
            }
            loginState.isError != null -> {
                Toast.makeText(
                    context,
                    "An error has occured, please try again!",
                    Toast.LENGTH_LONG
                ).show()

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
            text = stringResource(id = R.string.login),
            fontSize = 25.sp,
            color = colorResource(id = R.color.darkWhite),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        CustomOutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
            label = stringResource(id = R.string.email),
            isError = state.emailError != null,
            errorMessage = state.emailError ?: "",
            keyboardType = KeyboardType.Email,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(horizontal = 5.dp, vertical = 16.dp)
                .padding(start = 10.dp, end = 10.dp)
        )


        CustomOutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
            label = stringResource(id = R.string.passwowrd),
            isError = state.passwordError != null,
            errorMessage = state.passwordError ?: "",
            visualTransformation = PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .padding(horizontal = 5.dp, vertical = 16.dp)
                .padding(start = 10.dp, end = 10.dp)
        )


        Button(
            onClick = { viewModel.onEvent(LoginFormEvent.Submit) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isValidInputData) colorResource(id = R.color.orange) else colorResource(
                    id = R.color.gainsboro
                )
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.login),
                color = Color.Black)
        }

        TextButton(onClick = { /* Handle forgot password */ }) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                color = colorResource(id = R.color.blue_shade),
                textAlign = TextAlign.Center
            )
        }

        TextButton(onClick = { navigateToRegister() }) {
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
