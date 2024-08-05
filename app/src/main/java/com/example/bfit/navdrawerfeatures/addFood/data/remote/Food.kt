package com.example.bfit.navdrawerfeatures.addFood.data.remote

import com.google.gson.annotations.SerializedName

data class Food(
    @SerializedName("label") val label: String?,
    @SerializedName("nutrients") val nutrients: Nutrients?,
    @SerializedName("brand") val brand: String?
)
