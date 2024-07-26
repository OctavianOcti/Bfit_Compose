package com.example.bfit.util.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val FoodInfoType = object: NavType<FoodInfoModel>(
    isNullableAllowed = false
){
    override fun get(bundle: Bundle, key: String): FoodInfoModel? {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            bundle.getParcelable(key,FoodInfoModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): FoodInfoModel {
        return Json.decodeFromString<FoodInfoModel>(value)
    }

    override fun put(bundle: Bundle, key: String, value: FoodInfoModel) {
        bundle.putParcelable(key,value)
    }

    override fun serializeAsValue(value: FoodInfoModel): String {
        return Json.encodeToString(value)
    }

}