package com.example.bfit.navdrawerfeatures.quickAdd.presentation

sealed class QuickAddEvent {
    data class MealChanged(val meal: String): QuickAddEvent()
    data class KcalChanged(val kcal: String): QuickAddEvent()
    data class CarbsChanged(val carbs: String): QuickAddEvent()
    data class ProteinChanged(val protein: String): QuickAddEvent()
    data class FatChanged(val fat: String): QuickAddEvent()
    data class FoodNameChanged(val foodName: String): QuickAddEvent()
    data class ServingSizeChanged(val servingSize: String): QuickAddEvent()
    data class FormattedDateChanged(val formattedDate:String): QuickAddEvent()
    data class PreviousTotalKcalChanged(val previousTotalKcal:String): QuickAddEvent()
    data class PreviousTotalProteinChanged(val previousTotalProtein:String):QuickAddEvent()
    data class PreviousTotalCarbChanged(val previousTotalCarbChanged:String): QuickAddEvent()
    data class PreviousTotalFatChanged(val previousTotalFatChanged:String):QuickAddEvent()
    object SubmitData:QuickAddEvent()
}