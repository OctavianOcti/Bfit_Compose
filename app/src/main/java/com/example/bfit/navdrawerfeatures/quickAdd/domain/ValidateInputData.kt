package com.example.bfit.navdrawerfeatures.quickAdd.domain

import android.content.Context
import com.example.bfit.R

class ValidateInputData {
    fun execute(
        context: Context,
        meal:String,
        kcal:String,
        protein:String,
        carb:String,
        fat:String,
        foodName:String,
        servingSize: String)
    : Boolean{
        return meal.isNotEmpty() && meal!= context.getString(R.string.select_meal) &&
                kcal.isNotEmpty() && kcal!= context.getString(R.string.enter_calories_amount) &&
                protein.isNotEmpty() && protein!= context.getString(R.string.enter_protein_amount) &&
                carb.isNotEmpty() && carb!= context.getString(R.string.enter_carbs_amount) &&
                fat.isNotEmpty() && fat!= context.getString(R.string.enter_fat_amount) &&
                foodName.isNotEmpty() && foodName!= context.getString(R.string.enter_food_name) &&
                servingSize.isNotEmpty() && servingSize!= context.getString(R.string.enter_serving_size)

    }
}