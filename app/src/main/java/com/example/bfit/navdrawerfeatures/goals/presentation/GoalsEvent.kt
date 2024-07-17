package com.example.bfit.navdrawerfeatures.goals.presentation

sealed class GoalsEvent {
    data class GenderChanged(val gender: String) : GoalsEvent()
    data class AgeChanged(val age: String) : GoalsEvent()
    data class WeightChanged(val weight: String) : GoalsEvent()
    data class HeightChanged(val height: String) : GoalsEvent()
    data class GoalChanged(val goal: String) : GoalsEvent()
    data class ActivityLevelChanged(val activityLevel: String) : GoalsEvent()


    //object Submit: GoalsEvent()
}
