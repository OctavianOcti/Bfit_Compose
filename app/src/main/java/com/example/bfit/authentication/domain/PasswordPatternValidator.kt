package com.example.bfit.authentication.domain

interface PasswordPatternValidator {
    fun isValidPassword(password : String): Boolean
}