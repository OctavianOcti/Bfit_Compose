package com.example.bfit.navdrawerfeatures.quickAdd.presentation

data class QuickAddState (
    val meal : String = "",
    val kcal: String = "",
    val protein : String = "",
    val carbs: String = "",
    val fat : String = "",
    val foodName: String = "",
    val servingSize : String = "",
    val formattedDate:String = "",
    val previousTotalKcal:String ="",
    val previousTotalProtein:String= "",
    val previousTotalCarb:String ="",
    val previousTotalFat:String ="",
    val previousFoodKcal:String ="",
    val previousFoodProtein:String = "",
    val previousFoodCarb:String = "",
    val previousFoodFat:String ="",

)