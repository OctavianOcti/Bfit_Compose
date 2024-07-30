package com.example.bfit.navdrawerfeatures.showMealsFood.presentation

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel

sealed class ShowMealsFoodEvent {
    data class MealChanged(val meal: String): ShowMealsFoodEvent()
    data class FormattedDateChanged(val formattedDate: String): ShowMealsFoodEvent()
    data class DeleteFood(val food: FoodInfoModel):ShowMealsFoodEvent()
    object FoodChanged:ShowMealsFoodEvent()

}