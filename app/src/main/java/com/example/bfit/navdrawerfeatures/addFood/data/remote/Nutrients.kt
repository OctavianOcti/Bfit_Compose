package com.example.bfit.navdrawerfeatures.addFood.data.remote

import com.google.gson.annotations.SerializedName

data class Nutrients(
    @SerializedName("ENERC_KCAL") val enercKcal: Double?,
    @SerializedName("PROCNT") val procnt: Double?, // protein
    @SerializedName("FAT") val fat: Double?,
    @SerializedName("CHOCDF") val chocdf: Double?, // carbs
    @SerializedName("FIBTG") val fibtg: Double? // fiber
)
