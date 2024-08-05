package com.example.bfit.navdrawerfeatures.addFood.presentation

sealed class AddFoodEvent {
data class SearchForFood(val food:String) : AddFoodEvent()
    data class SearchTextChanged(val text:String): AddFoodEvent()
}