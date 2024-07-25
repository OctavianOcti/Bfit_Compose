package com.example.bfit.navdrawerfeatures.showMealsFood

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bfit.R

@Composable
@Preview(showBackground = true)
fun MealCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 10.dp, end = 8.dp, bottom = 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.ic_bfit_logo_background),
        ),
       // backgroundColor = colorResource(id = R.color.ic_bfit_logo_background),
        shape = RoundedCornerShape(20.dp),
        //elevation = 8.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Create references for the composables
            val (mealLabel, textServingSize, servingSize, servingType, caloriesLogged, textCaloriesLogged) = createRefs()

            Text(
                text = stringResource(id = R.string.food),
                color = colorResource(id = R.color.blueForDarkGrey),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(mealLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = stringResource(id = R.string.serving_size),
                color = colorResource(id = R.color.whiteDelimiter),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(textServingSize) {
                    start.linkTo(parent.start)
                    top.linkTo(mealLabel.bottom, margin = 3.dp)
                }
            )

            Text(
                text = stringResource(id = R.string._120),
                color = colorResource(id = R.color.orange),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(servingSize) {
                    start.linkTo(textServingSize.end, margin = 5.dp)
                    top.linkTo(textServingSize.top)
                    bottom.linkTo(textServingSize.bottom)
                }
            )

            Text(
                text = stringResource(id = R.string.g),
                color = colorResource(id = R.color.orange),
                modifier = Modifier.constrainAs(servingType) {
                    start.linkTo(servingSize.end, margin = 5.dp)
                    bottom.linkTo(servingSize.bottom)
                }
            )

            Text(
                text = stringResource(id = R.string._125),
                color = colorResource(id = R.color.orange),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(caloriesLogged) {
                    end.linkTo(parent.end, margin = 20.dp)
                    top.linkTo(textServingSize.bottom, margin = 14.dp)
                    bottom.linkTo(parent.bottom, margin = 5.dp)
                }
            )

            Text(
                text = stringResource(id = R.string.calories_logged),
                color = colorResource(id = R.color.whiteDelimiter),
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(textCaloriesLogged) {
                    end.linkTo(caloriesLogged.start, margin = 5.dp)
                    top.linkTo(caloriesLogged.top)
                }
            )
        }
    }
}