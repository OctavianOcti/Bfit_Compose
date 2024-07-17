package com.example.bfit.navdrawerfeatures.goals.domain.data

import com.example.bfit.navdrawerfeatures.goals.domain.HeightPatternValidator
import com.example.bfit.util.Constants

class AndroidHeightPatternValidator : HeightPatternValidator {
    override fun isValidHeight(height: String): Boolean {
        val maximumOneDecimalRegex = """^\d+(\.\d{1})?$""".toRegex()
        return maximumOneDecimalRegex.matches(height) && height.toFloatOrNull()?.let {
            it in Constants.MIN_HEIGHT..Constants.MAX_HEIGHT
        } ?: false
    }

}