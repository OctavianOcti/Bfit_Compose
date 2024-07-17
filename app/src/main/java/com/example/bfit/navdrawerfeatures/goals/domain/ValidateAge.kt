package com.example.bfit.navdrawerfeatures.goals.domain

import com.example.bfit.util.Constants

class ValidateAge {
    fun execute(age: String): Boolean {
        return age.toIntOrNull()?.let { it in Constants.MIN_AGE..Constants.MAX_AGE } ?: false
    }
}