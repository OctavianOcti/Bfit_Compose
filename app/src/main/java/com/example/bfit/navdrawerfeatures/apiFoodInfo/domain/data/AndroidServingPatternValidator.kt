package com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.data

import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.ServingPatternValidator


class AndroidServingPatternValidator : ServingPatternValidator {
    override fun isValidServing(serving: String): Boolean {
        val maximumOneDecimalRegex = """^\d+(\.\d{1})?$""".toRegex()
        return maximumOneDecimalRegex.matches(serving) && serving.toFloatOrNull()?.let {
           it > 0
        } ?: false
    }
}