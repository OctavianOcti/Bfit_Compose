package com.example.bfit.navdrawerfeatures.foodInfo.presentation

sealed class FoodInfoEvent {
    data class MealChanged(val meal:String):FoodInfoEvent()
    data class ServingSizeChanged(val servingSize:String):FoodInfoEvent()
    data class KcalChanged(val kcal:String):FoodInfoEvent()
    data class CarbsChanged(val carbs:String):FoodInfoEvent()
    data class ProteinChanged(val protein:String):FoodInfoEvent()
    data class FatChanged(val fat:String):FoodInfoEvent()
    data class FoodNameChanged(val foodName:String): FoodInfoEvent()
    data class FoodBrandChanged(val foodBrand: String): FoodInfoEvent()
    data class PreviousServingSizeChanged(val previousServingSize:String): FoodInfoEvent()
    data class PreviousFoodKcalChanged(val previousFoodKcal: String):FoodInfoEvent()
    data class PreviousFoodProteinChanged(val previousFoodProtein:String):FoodInfoEvent()
    data class PreviousFoodCarbChanged(val previousFoodCarb:String):FoodInfoEvent()
    data class PreviousFoodFatChanged(val previousFoodFat:String):FoodInfoEvent()
    data class MacrosChanged(val servingSize:String): FoodInfoEvent()
    data class FormattedDateChanged(val formattedDate:String): FoodInfoEvent()
    object SubmitData:FoodInfoEvent()
}