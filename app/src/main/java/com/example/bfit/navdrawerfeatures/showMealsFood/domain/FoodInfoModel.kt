package com.example.bfit.navdrawerfeatures.showMealsFood.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class FoodInfoModel(
    val label: String,
    val brand: String,
    val enercKcal: String,
    val prot: String,
    val fat: String,
    val carb: String,
    val fiber: String,
    val servingSize: String,
    val servingType: String
) : Parcelable {
    fun isEmpty(): Boolean {
        return label.isEmpty() &&
                brand.isEmpty() &&
                enercKcal.isEmpty() &&
                prot.isEmpty() &&
                fat.isEmpty() &&
                carb.isEmpty() &&
                fiber.isEmpty() &&
                servingSize.isEmpty() &&
                servingType.isEmpty()
    }

    override fun toString(): String {
        return "FoodInfoModel(" +
                "label='$label', " +
                "brand='$brand', " +
                "enercKcal='$enercKcal', " +
                "prot='$prot', " +
                "fat='$fat', " +
                "carb='$carb', " +
                "fiber='$fiber', " +
                "servingSize='$servingSize', " +
                "servingType='$servingType'" +
                ")"
    }
}
