package com.example.bfit.navdrawerfeatures.customMealFood.meal.createMeal.presentation

sealed class CreateMealEvent {
    data class MealNameChanged(val mealName: String): CreateMealEvent()
    data class KcalChanged(val kcal: String): CreateMealEvent()
    data class CarbsChanged(val carbs: String): CreateMealEvent()
    data class ProteinChanged(val protein: String): CreateMealEvent()
    data class FatChanged(val fat: String): CreateMealEvent()
    data object AddMeal: CreateMealEvent()
    data object SubmitData: CreateMealEvent()
}