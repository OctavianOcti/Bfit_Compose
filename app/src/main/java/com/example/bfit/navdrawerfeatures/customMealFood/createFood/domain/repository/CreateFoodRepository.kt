package com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain.repository

import com.example.bfit.util.Response

interface CreateFoodRepository {
    suspend fun setNewDocument(
        uid: String,
        foodMap: Map<String, Any>,
        foodLabel:String
    ): Response<Boolean>
}