package com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShowMealsFoodRepository {
    fun getFood(uid:String,formattedDate:String, meal:String) : Flow<Resource<List<FoodInfoModel>>>
}