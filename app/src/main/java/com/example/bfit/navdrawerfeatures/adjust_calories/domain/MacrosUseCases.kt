package com.example.bfit.navdrawerfeatures.adjust_calories.domain

data class MacrosUseCases(
    val validateCalorieAmount: ValidateCalorieAmount,
    val validateMacrosPercentages: ValidateMacrosPercentages,
    val getClosestDivisibleValue: GetClosestDivisibleValue
)