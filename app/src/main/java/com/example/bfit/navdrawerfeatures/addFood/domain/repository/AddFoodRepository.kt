package com.example.bfit.navdrawerfeatures.addFood.domain.repository

import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult

interface AddFoodRepository {
    suspend fun getFood(food:String): FoodAPIResult
    suspend fun getFoodByBarcode(upc:String) : FoodAPIResult
}