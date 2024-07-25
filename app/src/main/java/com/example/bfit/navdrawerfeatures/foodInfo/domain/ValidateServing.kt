package com.example.bfit.navdrawerfeatures.foodInfo.domain

class ValidateServing(
    private val validator: ServingPatternValidator
) {
    fun execute(serving:String) : Boolean{
        return validator.isValidServing(serving)
    }
}