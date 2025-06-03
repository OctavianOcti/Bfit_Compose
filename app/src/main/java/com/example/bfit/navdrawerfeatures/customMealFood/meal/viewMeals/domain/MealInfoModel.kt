package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MealInfoModel(
    val label: String,
    val total_kcal: String,
    val total_protein: String,
    val total_fat: String,
    val total_carb: String,
    val servingType: String
) : Parcelable {
    fun isEmpty(): Boolean {
        return label.isEmpty() &&
                 total_kcal.isEmpty() &&
                total_protein.isEmpty() &&
                total_fat.isEmpty() &&
                total_carb.isEmpty() &&
                servingType.isEmpty()
    }

    override fun toString(): String {
        return "MealInfoModel(" +
                "label='$label', " +
                "total_kcal='$total_kcal', " +
                "total_protein='$total_protein', " +
                "total_fat='$total_fat', " +
                "total_carb='$total_carb', " +
                "servingType='$servingType', " +
                ")"
    }
    }
