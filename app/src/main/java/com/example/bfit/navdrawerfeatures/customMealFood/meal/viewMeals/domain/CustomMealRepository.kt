package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface CustomMealRepository {
    fun getMeals(uid:String) : Flow<Resource<List<MealInfoModel>>>
    suspend fun deleteMealFromDatabase(
        uid:String,
        meal: MealInfoModel,
    ): Response<Boolean>
}