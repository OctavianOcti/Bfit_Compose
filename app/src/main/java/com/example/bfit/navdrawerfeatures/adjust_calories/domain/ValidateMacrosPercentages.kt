package com.example.bfit.navdrawerfeatures.adjust_calories.domain

import com.example.bfit.util.Constants

class ValidateMacrosPercentages {
    fun execute(proteinPercentage: Float,carbPercentage:Float,fatPercentage:Float): Boolean {
        return (proteinPercentage + carbPercentage + fatPercentage).toDouble() == 100.0
    }
}