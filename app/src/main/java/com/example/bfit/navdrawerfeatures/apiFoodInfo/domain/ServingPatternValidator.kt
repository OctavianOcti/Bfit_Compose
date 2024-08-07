package com.example.bfit.navdrawerfeatures.apiFoodInfo.domain

interface ServingPatternValidator {
    fun isValidServing(serving: String): Boolean
}