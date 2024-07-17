package com.example.bfit.navdrawerfeatures.goals.domain

interface WeightPatternValidator {
    fun isValidWeight(weight: String): Boolean
}