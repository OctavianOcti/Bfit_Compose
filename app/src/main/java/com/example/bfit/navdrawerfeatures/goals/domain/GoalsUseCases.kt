package com.example.bfit.navdrawerfeatures.goals.domain

data class GoalsUseCases(
    val validateAge: ValidateAge,
    val validateWeight: ValidateWeight,
    val validateHeight: ValidateHeight,
    val validateInputData: ValidateInputData
)