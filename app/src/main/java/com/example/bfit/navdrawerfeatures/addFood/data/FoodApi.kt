package com.example.bfit.navdrawerfeatures.addFood.data

import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("parser")
    suspend fun doGetFoodInfo(
        @Query("app_id") appId: String,
        @Query("app_key") appKey: String,
        @Query("ingr") ingr: String
    ): FoodAPIResult
}