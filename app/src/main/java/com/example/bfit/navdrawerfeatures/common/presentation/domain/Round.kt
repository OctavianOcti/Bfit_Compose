package com.example.bfit.navdrawerfeatures.common.presentation.domain

import kotlin.math.pow
import kotlin.math.roundToInt

fun round(value: Double): Double {
    val scale = 10.0.pow(1)
    return (value * scale).roundToInt() / scale
}