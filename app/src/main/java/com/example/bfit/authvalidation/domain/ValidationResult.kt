package com.example.bfit.authvalidation.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
