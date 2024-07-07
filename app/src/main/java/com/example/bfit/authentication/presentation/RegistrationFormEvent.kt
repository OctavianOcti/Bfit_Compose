package com.example.bfit.authentication.presentation

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegistrationFormEvent()

    object CheckData: RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}
