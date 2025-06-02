package com.example.bfit.navdrawerfeatures.customMealFood.viewFood.presentation

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel

sealed class CustomFoodEvent {
    data class DeleteFood(val food: FoodInfoModel): CustomFoodEvent()
}