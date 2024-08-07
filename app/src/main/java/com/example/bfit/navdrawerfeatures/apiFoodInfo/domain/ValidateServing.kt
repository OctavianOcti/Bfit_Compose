package com.example.bfit.navdrawerfeatures.apiFoodInfo.domain

class ValidateServing(
    private val validator: ServingPatternValidator
) {
    fun execute(serving:String) : Boolean{
        return validator.isValidServing(serving)
    }
}