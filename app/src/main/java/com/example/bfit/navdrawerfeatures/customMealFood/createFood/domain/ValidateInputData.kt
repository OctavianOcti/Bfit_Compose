package com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain

import android.content.Context
import com.example.bfit.R

class ValidateInputData {
    fun execute(
        context: Context,
        foodName:String,
        kcal:String,
        protein:String,
        carb:String,
        fat:String,
        ): Boolean{

       return  kcal.isNotEmpty() && kcal!= context.getString(R.string.enter_calories_amount) &&
                protein.isNotEmpty() && protein!= context.getString(R.string.enter_protein_amount) &&
                carb.isNotEmpty() && carb!= context.getString(R.string.enter_carbs_amount) &&
                fat.isNotEmpty() && fat!= context.getString(R.string.enter_fat_amount) &&
                foodName.isNotEmpty() && foodName!= context.getString(R.string.enter_food_name)

    }
}