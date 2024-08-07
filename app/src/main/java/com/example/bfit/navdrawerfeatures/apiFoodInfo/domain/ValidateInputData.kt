package com.example.bfit.navdrawerfeatures.apiFoodInfo.domain

import android.content.Context
import com.example.bfit.R

class ValidateInputData {
    fun execute(
        context: Context,
        meal:String
    )
    : Boolean{
        return meal.isNotEmpty() && meal!= context.getString(R.string.select_meal)
    }
}