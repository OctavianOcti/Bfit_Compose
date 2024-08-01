package com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface ShowMealsFoodRepository {
    fun getFood(uid:String,formattedDate:String, meal:String) : Flow<Resource<List<FoodInfoModel>>>
    suspend fun updateMacros(
        uid:String,
        formattedDate:String,
        food:FoodInfoModel
    ): Response<Boolean>
    suspend fun deleteFoodFromDatabase(
        uid:String,
        formattedDate:String,
        food:FoodInfoModel,
        meal:String
    ): Response<Boolean>
}