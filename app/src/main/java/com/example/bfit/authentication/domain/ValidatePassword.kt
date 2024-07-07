package com.example.bfit.authentication.domain

class ValidatePassword(
    private val validator : PasswordPatternValidator
) {

    fun execute(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isDigit() } &&
                password.any { it.isLetter() }
        if(!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }
        if(!validator.isValidPassword(password)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one symbol"
            )
        }
        val containsUpperCase = password.any {it.isUpperCase()}
        if(!containsUpperCase){
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one upper case"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}