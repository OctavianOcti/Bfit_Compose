package com.example.bfit.navdrawerfeatures.apiFoodInfo.presentation

data class ApiFoodInfoState(
    val meal: String = "",
    val servingSize: String = "",
    val kcal: String = "",
    val carbs: String = "",
    val protein: String = "",
    val fat: String = "",
    val foodName: String = "",
    val foodBrand: String = "",
    val previousServingSize: String = "",
    val previousFoodKcal:String ="",
    val previousFoodProtein:String = "",
    val previousFoodCarb:String = "",
    val previousFoodFat:String ="",
    val formattedDate:String = "",
    val previousTotalKcal:String ="",
    val previousTotalProtein:String= "",
    val previousTotalCarb:String ="",
    val previousTotalFat:String =""

)