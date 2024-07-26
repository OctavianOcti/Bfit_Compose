package com.example.bfit.util.navigation

import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import kotlinx.serialization.Serializable

@Serializable
object Register

@Serializable
object Login

@Serializable
object Main

@Serializable
object Home

@Serializable
object Profile

@Serializable
object Goals

@Serializable
object Diary

@Serializable
data class AdjustMacros(val userInfo : List<String>)

@Serializable
object AddFood

@Serializable
object QuickAdd

@Serializable
data class ShowMealsFood(val meal: String, val formattedDate: String )

@Serializable
data class FoodInfo(val foodInfo: FoodInfoModel, val meal:String,val formattedDate: String)

