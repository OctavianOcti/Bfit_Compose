package com.example.bfit.navdrawerfeatures.foodInfo.domain.data

import com.example.bfit.navdrawerfeatures.foodInfo.domain.ServingPatternValidator

class AndroidServingPatternValidator : ServingPatternValidator {
    override fun isValidServing(serving: String): Boolean {
        val maximumOneDecimalRegex = """^\d+(\.\d{1})?$""".toRegex()
        return maximumOneDecimalRegex.matches(serving) && serving.toFloatOrNull()?.let {
           it > 0
        } ?: false
    }
}