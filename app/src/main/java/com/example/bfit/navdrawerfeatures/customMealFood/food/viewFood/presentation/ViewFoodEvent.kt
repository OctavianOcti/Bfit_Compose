package com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.presentation

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel

sealed class ViewFoodEvent {
    data class DeleteFood(val food: FoodInfoModel): ViewFoodEvent()
}