package com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.common.presentation.Divider
import com.example.bfit.navdrawerfeatures.common.presentation.FieldRow
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddState

@Composable
fun LayoutWithFields(
    state : CreateFoodState,
    onFoodNameClick: () -> Unit,
    onKcalClick: () -> Unit,
    onCarbClick: () -> Unit,
    onProteinClick: () -> Unit,
    onFatClick: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 25.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorResource(id = R.color.ic_bfit_logo_background)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            FieldRow(
                label = stringResource(id = R.string.food_name),
                value = state.foodName.ifEmpty { stringResource(id = R.string.enter_food_name) },
                onFieldClick = onFoodNameClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()

            FieldRow(
                label = stringResource(id = R.string.kcal),
                value = state.kcal.ifEmpty { stringResource(id = R.string.enter_calories_amount) },
                onFieldClick = onKcalClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.protein_g),
                value = state.protein.ifEmpty { stringResource(id = R.string.enter_protein_amount) },
                onFieldClick = onProteinClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.carbohydrates),
                value = state.carbs.ifEmpty { stringResource(id = R.string.enter_carbs_amount) },
                onFieldClick = onCarbClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.fat_g),
                value = state.fat.ifEmpty { stringResource(id = R.string.enter_fat_amount) },
                onFieldClick = onFatClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)

            )



        }
    }
}