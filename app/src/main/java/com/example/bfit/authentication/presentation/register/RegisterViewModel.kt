package com.example.bfit.authentication.presentation.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.authentication.domain.repository.AuthRepository
import com.example.bfit.authentication.domain.use_case.AuthUseCases
import com.example.bfit.authentication.presentation.AuthState
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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
//    private val validateEmail: ValidateEmail,
//    private val validatePassword: ValidatePassword,
//    private val validateRepeatedPassword: ValidateRepeatedPassword,
//    private val registerUserUseCase: RegisterUser
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private var _registerState = MutableStateFlow(value = AuthState())
    val registerState = _registerState.asStateFlow()

     var state by mutableStateOf(RegisterFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                validatePassword()
            }

            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
                validateRepeatedPassword()
            }

            is RegistrationFormEvent.Register -> {
                registerUser(state.email, state.password)
            }

            is RegistrationFormEvent.Submit -> {
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

    private fun validateRepeatedPassword() {
        //val repeatedPasswordResult = validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        val repeatedPasswordResult =
            authUseCases.validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        state = state.copy(repeatedPasswordError = repeatedPasswordResult.errorMessage)
    }

    private fun submitData() {
        val emailResult = authUseCases.validateEmail.execute(state.email)
        val passwordResult = authUseCases.validatePassword.execute(state.password)
        val repeatedPasswordResult = authUseCases.validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,

            ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

//    private fun registerUser(email: String, password: String) = viewModelScope.launch {
//        repository.registerUser(email = email, password = password).collectLatest { result ->
//            when (result) {
//                is Resource.Loading -> {
//                }
//
//                is Resource.Success -> {
//                    _registerState.update { it.copy(isSuccess = true) }
//
//                }
//
//                is Resource.Error -> {
//                    _registerState.update { it.copy(isError = result.message) }
//                }
//            }
//        }
//    }
    private fun registerUser(email: String, password: String) = viewModelScope.launch {
        authUseCases.registerUser(email = email, password = password).collectLatest { result ->
            when (result) {
                is Resource.Loading -> {

                    Log.d("RegisterViewModel", "Resource.Loading")
                }

                is Resource.Success -> {
                    _registerState.update {
                        it.copy(isSuccess = true)
                    }
                        Log.d("RegisterViewModel", "Resource.Success")

                }

                is Resource.Error -> {
                    _registerState.update { it.copy(isError = result.message) }
                    Log.d("RegisterViewModel", "Resource")
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}