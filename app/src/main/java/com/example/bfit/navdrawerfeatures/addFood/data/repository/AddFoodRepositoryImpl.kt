package com.example.bfit.navdrawerfeatures.addFood.data.repository

import com.example.bfit.navdrawerfeatures.addFood.data.FoodApi
import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult
import com.example.bfit.navdrawerfeatures.addFood.domain.repository.AddFoodRepository
import com.example.bfit.util.Constants
import javax.inject.Inject

class AddFoodRepositoryImpl @Inject constructor(
    private val foodApi: FoodApi
) : AddFoodRepository {
    override suspend fun getFood(food:String): FoodAPIResult {
        return foodApi.doGetFoodInfo(Constants.APP_ID,Constants.APP_KEY,food)
    }

    override suspend fun getFoodByBarcode(upc: String): FoodAPIResult {
        return foodApi.doGetFoodInfoByBarcode(Constants.APP_ID,Constants.APP_KEY, upc)
    }
}