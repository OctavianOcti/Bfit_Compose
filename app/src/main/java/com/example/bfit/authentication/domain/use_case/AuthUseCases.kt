package com.example.bfit.authentication.domain.use_case

import com.example.bfit.authentication.domain.use_case.register.RegisterUser
import com.example.bfit.authentication.domain.use_case.register.ValidateRepeatedPassword

data class AuthUseCases(
    val validateEmail : ValidateEmail,
    val validatePassword: ValidatePassword,
    val validateRepeatedPassword: ValidateRepeatedPassword,
    val registerUser: RegisterUser
)

