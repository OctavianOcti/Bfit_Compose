package com.example.bfit.navdrawerfeatures.quickAdd.domain.repository

import com.example.bfit.navdrawerfeatures.foodInfo.domain.PreviousMacros
import com.example.bfit.navdrawerfeatures.quickAdd.domain.PreviousFoodMacros
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface QuickAddRepository {
    fun getPreviousTotalMacros(
        uid:String,
        formattedDate:String
    ): Flow<Resource<PreviousMacros>>

    fun getPreviousFoodMacros(
        uid:String,
        formattedDate:String,
        meal:String,
        foodLabel:String
    ): Flow<Resource<PreviousFoodMacros>>

    suspend fun updateFoodDocument(
        uid:String,
        formattedDate: String,
        dayInfoMap:Map<String, Any>,
        foodMap:Map<String,Any>,
        kcal:String,
        protein:String,
        carb:String,
        fat:String,
        previousTotalKcal:String,
        previousTotalProtein:String,
        previousTotalCarb:String,
        previousTotalFat:String,
        meal:String,
        foodLabel:String
    ): Response<Boolean>

    suspend fun setNewDocument(
        uid: String,
        formattedDate: String,
        dayInfoMap: Map<String, Any>,
        foodMap: Map<String, Any>,
        meal:String,
        foodLabel:String
    ): Response<Boolean>

}