package com.example.bfit.navdrawerfeatures.customMealFood.meal.createMeal.presentation

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

@Composable
fun LayoutWithFields(
    state : CreateMealState,
    onMealNameClick: () -> Unit,
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
                label = stringResource(id = R.string.meal_name),
                value = state.mealName.ifEmpty { stringResource(id = R.string.enter_meal_name) },
                onFieldClick = onMealNameClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()

            FieldRow(
                label = stringResource(id = R.string.kcal_simple),
                value = state.kcal,
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.carbs_g),
                value = state.carbs,
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.protein),
                value = state.protein,
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.fat),
                value = state.fat,
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)

            )
        }
    }
}