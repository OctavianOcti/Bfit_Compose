package com.example.bfit.navdrawerfeatures.addFood.data.remote

import com.google.gson.annotations.SerializedName

data class FoodAPIResult(
    @SerializedName("hints")
    val hints: List<Hint>
)