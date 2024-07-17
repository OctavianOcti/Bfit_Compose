package com.example.bfit.navdrawerfeatures.goals.domain

import android.content.Context
import com.example.bfit.R

class ValidateInputData {
    fun execute(
        context: Context,
        gender:String,
        weight:String,
        height:String,
        age:String,
        activityLevel:String,
        goal:String)
    : Boolean{
        return gender.isNotEmpty() && gender!= context.getString(R.string.set_gender) &&
                weight.isNotEmpty() && weight!= context.getString(R.string.set_current_weight) &&
                height.isNotEmpty() && height!= context.getString(R.string.set_current_height) &&
                age.isNotEmpty() && age!= context.getString(R.string.set_current_age) &&
                activityLevel.isNotEmpty() && activityLevel!= context.getString(R.string.set_current_activity_level) &&
                goal.isNotEmpty() && goal!= context.getString(R.string.set_your_goal)

    }
}