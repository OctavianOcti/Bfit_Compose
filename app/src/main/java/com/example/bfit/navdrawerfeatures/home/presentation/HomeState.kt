package com.example.bfit.navdrawerfeatures.home.presentation

data class HomeState(
    val formattedDate:String="",
    val dailyCalories:Double=0.0,
    val dailyProtein:Double=0.0,
    val dailyCarb:Double=0.0,
    val dailyFat:Double=0.0,
    val kcalLeft:Double=0.0,
    val caloriesProgress:Float=0.0f,
    val carbsProgress:Float=0.0f,
    val proteinProgress:Float=0.0f,
    val fatProgress:Float=0.0f
)
