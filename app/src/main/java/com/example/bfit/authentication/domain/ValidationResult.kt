package com.example.bfit.authentication.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
