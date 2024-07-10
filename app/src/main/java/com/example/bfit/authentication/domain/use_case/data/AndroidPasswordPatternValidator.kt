package com.example.bfit.authentication.domain.use_case.data

import com.example.bfit.authentication.domain.use_case.PasswordPatternValidator

class AndroidPasswordPatternValidator: PasswordPatternValidator {
    override fun isValidPassword(password: String): Boolean {
        val hasSymbol = Regex("^(?=.*[_.()]).*$").containsMatchIn(password)
        return hasSymbol;
    }
}


