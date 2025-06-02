package com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain

class ValidateData(
    private val validator: QuantityPatternValidator
) {
    fun execute(data:String) : Boolean{
        return validator.isValidData(data)
    }
}