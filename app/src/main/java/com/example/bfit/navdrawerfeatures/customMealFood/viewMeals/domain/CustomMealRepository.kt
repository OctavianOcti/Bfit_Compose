package com.example.bfit.navdrawerfeatures.customMealFood.viewMeals.domain

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface CustomMealRepository {
    fun getFood(uid:String) : Flow<Resource<List<FoodInfoModel>>>
    suspend fun deleteFoodFromDatabase(
        uid:String,
        food: FoodInfoModel,
    ): Response<Boolean>
}