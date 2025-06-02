package com.example.bfit.navdrawerfeatures.customMealFood.createFood.presentation

import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddEvent

sealed class CreateFoodEvent {
    data class FoodNameChanged(val foodName: String): CreateFoodEvent()
    data class KcalChanged(val kcal: String): CreateFoodEvent()
    data class CarbsChanged(val carbs: String): CreateFoodEvent()
    data class ProteinChanged(val protein: String): CreateFoodEvent()
    data class FatChanged(val fat: String): CreateFoodEvent()
    data object SubmitData:CreateFoodEvent()

}