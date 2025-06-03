package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.presentation

import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.MealInfoModel

sealed class ViewMealsEvent {
    data class DeleteMeals(val meal: MealInfoModel): ViewMealsEvent()
}