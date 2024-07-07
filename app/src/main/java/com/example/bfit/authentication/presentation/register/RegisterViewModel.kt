package com.example.bfit.authentication.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.authentication.data.AndroidEmailPatternValidator
import com.example.bfit.authentication.data.AndroidPasswordPatternValidator
import com.example.bfit.authentication.domain.ValidateEmail
import com.example.bfit.authentication.domain.ValidatePassword
import com.example.bfit.authentication.domain.ValidateRepeatedPassword
import com.example.bfit.authentication.presentation.RegistrationFormEvent
import com.example.bfit.authentication.presentation.RegistrationFormState

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(AndroidEmailPatternValidator()),
    private val validatePassword: ValidatePassword = ValidatePassword(AndroidPasswordPatternValidator()),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
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

            is RegistrationFormEvent.CheckData -> {

            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }
    private fun validateEmail() {
        val emailResult = validateEmail.execute(state.email)
        state = state.copy(emailError = emailResult.errorMessage)
    }

    private fun validatePassword() {
        val passwordResult = validatePassword.execute(state.password)
        state = state.copy(passwordError = passwordResult.errorMessage)
    }

    private fun validateRepeatedPassword() {
        val repeatedPasswordResult = validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        state = state.copy(repeatedPasswordError = repeatedPasswordResult.errorMessage)
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(
            state.password, state.repeatedPassword
        )

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,

        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
            )
            return
        }
//        viewModelScope.launch {
//            validationEventChannel.send(ValidationEvent.Success)
//        }
    }


    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}