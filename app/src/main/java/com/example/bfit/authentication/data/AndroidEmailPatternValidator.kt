package com.example.bfit.authentication.data

import android.util.Patterns
import com.example.bfit.authentication.domain.EmailPatternValidator

class AndroidEmailPatternValidator : EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}