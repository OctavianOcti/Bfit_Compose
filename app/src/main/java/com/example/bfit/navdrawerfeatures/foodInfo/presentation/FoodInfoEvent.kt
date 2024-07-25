package com.example.bfit.navdrawerfeatures.foodInfo.presentation

sealed class FoodInfoEvent {
    data class MealChanged(val meal:String):FoodInfoEvent()
    data class ServingSizeChanged(val servingSize:String):FoodInfoEvent()
    data class KcalChanged(val kcal:String):FoodInfoEvent()
    data class CarbsChanged(val carbs:String):FoodInfoEvent()
    data class ProteinChanged(val protein:String):FoodInfoEvent()
    data class FatChanged(val fat:String):FoodInfoEvent()
}