package com.example.bfit.navdrawerfeatures.addFood.presentation

import com.example.bfit.navdrawerfeatures.addFood.data.remote.FoodAPIResult

data class FoodDetailState(
    val isLoading: Boolean = false,
    val foodDetail: FoodAPIResult? = null,
    val error: String = ""
)