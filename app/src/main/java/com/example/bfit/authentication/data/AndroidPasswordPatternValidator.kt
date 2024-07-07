package com.example.bfit.authentication.data

import com.example.bfit.authentication.domain.PasswordPatternValidator
import com.example.bfit.authentication.domain.ValidationResult

class AndroidPasswordPatternValidator:PasswordPatternValidator{
    override fun isValidPassword(password: String): Boolean {
        val hasSymbol = Regex("^(?=.*[_.()]).*$").containsMatchIn(password)
        return hasSymbol;
    }
}


