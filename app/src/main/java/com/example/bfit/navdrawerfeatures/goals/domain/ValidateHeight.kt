package com.example.bfit.navdrawerfeatures.goals.domain

class ValidateHeight(
    private val validator : HeightPatternValidator
) {
    fun execute(height: String):Boolean{
        return validator.isValidHeight(height)
    }
}