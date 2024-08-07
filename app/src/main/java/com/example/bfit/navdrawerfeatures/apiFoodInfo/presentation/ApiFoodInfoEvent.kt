package com.example.bfit.navdrawerfeatures.apiFoodInfo.presentation

import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddEvent

sealed class ApiFoodInfoEvent {
    data class MealChanged(val meal:String): ApiFoodInfoEvent()
    data class ServingSizeChanged(val servingSize:String): ApiFoodInfoEvent()
    data class KcalChanged(val kcal:String): ApiFoodInfoEvent()
    data class CarbsChanged(val carbs:String): ApiFoodInfoEvent()
    data class ProteinChanged(val protein:String): ApiFoodInfoEvent()
    data class FatChanged(val fat:String): ApiFoodInfoEvent()
    data class FoodNameChanged(val foodName:String): ApiFoodInfoEvent()
    data class FoodBrandChanged(val foodBrand: String): ApiFoodInfoEvent()
    data class PreviousServingSizeChanged(val previousServingSize:String): ApiFoodInfoEvent()
    data class PreviousFoodKcalChanged(val previousFoodKcal: String): ApiFoodInfoEvent()
    data class PreviousFoodProteinChanged(val previousFoodProtein:String): ApiFoodInfoEvent()
    data class PreviousFoodCarbChanged(val previousFoodCarb:String): ApiFoodInfoEvent()
    data class PreviousFoodFatChanged(val previousFoodFat:String): ApiFoodInfoEvent()
    data class MacrosChanged(val servingSize:String): ApiFoodInfoEvent()
    data class FormattedDateChanged(val formattedDate:String): ApiFoodInfoEvent()
    data class PreviousTotalKcalChanged(val previousTotalKcal:String): ApiFoodInfoEvent()
    data class PreviousTotalProteinChanged(val previousTotalProtein:String): ApiFoodInfoEvent()
    data class PreviousTotalCarbChanged(val previousTotalCarbChanged:String): ApiFoodInfoEvent()
    data class PreviousTotalFatChanged(val previousTotalFatChanged:String): ApiFoodInfoEvent()
    object SubmitData: ApiFoodInfoEvent()
}