package com.example.bfit.navdrawerfeatures.adjust_calories.domain

class GetClosestDivisibleValue {
    fun execute(value: Int, divisibleBy: Int): Int {
        val quotient = value / divisibleBy
        val lowerValue = quotient * divisibleBy
        val upperValue = (quotient + 1) * divisibleBy
        return if (value - lowerValue < upperValue - value) {
            lowerValue
        } else {
            upperValue
        }
    }

}