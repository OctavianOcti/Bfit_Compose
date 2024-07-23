package com.example.bfit.navdrawerfeatures.adjust_calories.presentation

import com.example.bfit.main.domain.model.UserInfo

sealed class MacrosEvent {
    data class CalorieChanged(val calorie: String) : MacrosEvent()
    data class ProteinChanged(val protein: String) : MacrosEvent()
    data class CarbChanged(val carb: String) : MacrosEvent()
    data class FatChanged(val fat: String) : MacrosEvent()
    data class ProteinPercentageChanged(val proteinPercentage: String): MacrosEvent()
    data class CarbPercentageChanged(val carbPercentage: String): MacrosEvent()
    data class FatPercentageChanged(val fatPercentage:String): MacrosEvent()
    data class NewUser(val userInfoList: List<String>): MacrosEvent()
    data class ExistingUser(val userInfo: UserInfo, ): MacrosEvent()
    data class SaveMacros(
        val userInfoList: List<String>,
        val userExists:Boolean,
        val gender: String,
        val activityLevel: String,
        val goal: String,
        val age: Int,
        val weight: Float,
        val height: Float,
        val protein: Int,
        val carb: Int,
        val fat: Int,
        val proteinPercentage: Float,
        val carbPercentage: Float,
        val fatPercentage: Float,
        val calories: Int
    ): MacrosEvent()
    data class ResetMacros(val userInfoList: List<String>): MacrosEvent()
    //object Submit: GoalsEvent()
}