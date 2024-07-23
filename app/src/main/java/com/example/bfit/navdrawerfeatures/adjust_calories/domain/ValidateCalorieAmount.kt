package com.example.bfit.navdrawerfeatures.adjust_calories.domain

import com.example.bfit.util.Constants

class ValidateCalorieAmount {
    fun execute(calorieAmount: String): Boolean {
        return calorieAmount.toIntOrNull()?.let { it in Constants.MIN_CALORIE..Constants.MAX_CALORIE } ?: false
    }
}