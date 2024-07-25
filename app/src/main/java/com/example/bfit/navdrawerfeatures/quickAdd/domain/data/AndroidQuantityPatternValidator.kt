package com.example.bfit.navdrawerfeatures.quickAdd.domain.data

import com.example.bfit.navdrawerfeatures.quickAdd.domain.QuantityPatternValidator

class AndroidQuantityPatternValidator : QuantityPatternValidator {
    override fun isValidData(data: String): Boolean {
        val maximumOneDecimalRegex = """^\d+(\.\d{1})?$""".toRegex()
        return maximumOneDecimalRegex.matches(data) && data.toFloatOrNull()?.let {
           it > 0
        } ?: false
    }
}