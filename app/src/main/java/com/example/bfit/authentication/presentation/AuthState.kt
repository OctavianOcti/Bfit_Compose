package com.example.bfit.authentication.presentation

data class AuthState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String? = null
)