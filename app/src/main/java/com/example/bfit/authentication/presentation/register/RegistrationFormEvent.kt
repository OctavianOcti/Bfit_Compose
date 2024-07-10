package com.example.bfit.authentication.presentation.register

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegistrationFormEvent()

    object Register: RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}
