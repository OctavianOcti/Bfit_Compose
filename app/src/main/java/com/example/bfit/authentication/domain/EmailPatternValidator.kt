package com.example.bfit.authentication.domain

interface EmailPatternValidator {

    fun isValidEmail(email: String): Boolean

}