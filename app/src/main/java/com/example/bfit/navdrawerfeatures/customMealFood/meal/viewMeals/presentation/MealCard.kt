package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.MealInfoModel

@Composable
fun MealCard(
    mealInfoModel: MealInfoModel,
    onCLick: () -> Unit ={}
) {
    Card(
        modifier = Modifier
            .background(colorResource(id = R.color.ic_bfit_logo_background))
            .fillMaxWidth()
            .padding(start = 8.dp, top = 10.dp, end = 8.dp, bottom = 5.dp)
            .clickable { onCLick() },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.ic_bfit_logo_background),
        ),
        //backgroundColor = colorResource(id = R.color.ic_bfit_logo_background),
        shape = RoundedCornerShape(20.dp),
        //elevation = 8.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Create references for the composables
            val (mealLabel, caloriesLogged,) = createRefs()

            Text(
                text = mealInfoModel.label,
                color = colorResource(id = R.color.blueForDarkGrey),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(mealLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )


            Text(
                text = mealInfoModel.total_kcal + " kcal",
                color = colorResource(id = R.color.orange),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(caloriesLogged) {
                    start.linkTo(mealLabel.start)
                    top.linkTo(mealLabel.bottom, margin = 14.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                }
            )

        }
    }
}