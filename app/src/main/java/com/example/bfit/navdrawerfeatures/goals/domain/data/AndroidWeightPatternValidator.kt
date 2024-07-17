package com.example.bfit.navdrawerfeatures.goals.domain.data

import com.example.bfit.navdrawerfeatures.goals.domain.WeightPatternValidator
import com.example.bfit.util.Constants

class AndroidWeightPatternValidator: WeightPatternValidator {
    override fun isValidWeight(weight: String): Boolean {
        val maximumOneDecimalRegex = """^\d+(\.\d{1})?$""".toRegex()
        return maximumOneDecimalRegex.matches(weight) && weight.toFloatOrNull()?.let {
            it in Constants.MIN_WEIGHT..Constants.MAX_WEIGHT
        } ?: false
    }

}