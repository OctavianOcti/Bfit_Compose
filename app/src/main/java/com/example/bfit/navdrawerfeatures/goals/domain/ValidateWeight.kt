package com.example.bfit.navdrawerfeatures.goals.domain

class ValidateWeight(
    private val validator : WeightPatternValidator
) {
    fun execute(weight:String) : Boolean{
        return validator.isValidWeight(weight)
    }
}