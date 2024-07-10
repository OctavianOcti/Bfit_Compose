package com.example.bfit.authentication.domain.use_case

interface EmailPatternValidator {

    fun isValidEmail(email: String): Boolean

}