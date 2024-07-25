package com.example.bfit.navdrawerfeatures.foodInfo.domain

interface ServingPatternValidator {
    fun isValidServing(serving: String): Boolean
}