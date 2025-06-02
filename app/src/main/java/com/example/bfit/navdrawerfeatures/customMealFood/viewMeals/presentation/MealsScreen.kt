package com.example.bfit.navdrawerfeatures.customMealFood.viewMeals.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.customMealFood.common.presentation.FoodMealSection

@Composable
fun MealsScreen(){
    FoodMealSection(
        onClick = {},
        text = stringResource(R.string.create_a_meal),
        painter = painterResource(id= R.drawable.meal),
        imageContentDescription = stringResource(R.string.create_a_meal)
    )
}