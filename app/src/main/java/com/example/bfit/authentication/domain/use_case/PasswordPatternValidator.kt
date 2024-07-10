package com.example.bfit.authentication.domain.use_case

interface PasswordPatternValidator {
    fun isValidPassword(password : String): Boolean
}