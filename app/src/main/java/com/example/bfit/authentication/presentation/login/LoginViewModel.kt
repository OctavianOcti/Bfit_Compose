package com.example.bfit.authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.authentication.domain.repository.AuthRepository
import com.example.bfit.authentication.domain.use_case.AuthUseCases
import com.example.bfit.authentication.presentation.AuthState
import com.example.bfit.authentication.presentation.register.RegistrationFormEvent
import com.example.bfit.authentication.presentation.register.RegisterFormState
import com.example.bfit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
//    private val validateEmail: ValidateEmail,
//    private val validatePassword: ValidatePassword,
//    private val validateRepeatedPassword: ValidateRepeatedPassword,
//    private val registerUserUseCase: RegisterUser
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private var _loginState = MutableStateFlow(value = AuthState())
    val loginState = _loginState.asStateFlow()

    var state by mutableStateOf(LoginFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                validatePassword()
            }


            is LoginFormEvent.Login -> {
                loginUser(state.email, state.password)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun validateEmail() {
        // val emailResult = validateEmail.execute(state.email)
        val emailResult = authUseCases.validateEmail.execute(state.email)
        state = state.copy(emailError = emailResult.errorMessage)
    }

    private fun validatePassword() {
        // val passwordResult = validatePassword.execute(state.password)
        val passwordResult = authUseCases.validatePassword.execute(state.password)
        state = state.copy(passwordError = passwordResult.errorMessage)
    }


    private fun submitData() {
        val emailResult = authUseCases.validateEmail.execute(state.email)
        val passwordResult = authUseCases.validatePassword.execute(state.password)


        val hasError = listOf(
            emailResult,
            passwordResult,

            ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email = email, password = password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    _loginState.update { it.copy(isSuccess = true) }

                }

                is Resource.Error -> {
                    _loginState.update { it.copy(isError = result.message) }
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}