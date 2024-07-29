package com.example.bfit.navdrawerfeatures.foodInfo.domain.repository

import com.example.bfit.navdrawerfeatures.foodInfo.domain.PreviousMacros
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface FoodInfoRepository {
    fun getPreviousTotalMacros(
        uid:String,
        formattedDate:String,
    ): Flow<Resource<PreviousMacros>>
    fun updateDaysDocument(uid:String,
                           formattedDate:String,
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
                           previousFoodKcal:String,
                           previousFoodProtein:String,
                           previousFoodCarb:String,
                           previousFoodFat:String,
                           meal:String,
                           foodLabel:String

    ) : Response<Boolean>

}