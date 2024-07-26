package com.example.bfit.navdrawerfeatures.showMealsFood.presentation

sealed class ShowMealsFoodEvent {
    data class MealChanged(val meal: String): ShowMealsFoodEvent()
    data class FormattedDateChanged(val formattedDate: String): ShowMealsFoodEvent()
    object FoodChanged:ShowMealsFoodEvent()

}