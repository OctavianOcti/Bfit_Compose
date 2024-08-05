package com.example.bfit.navdrawerfeatures.addFood.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel

@Composable
fun ApiCard(
    foodInfoModel: FoodInfoModel,
    onCLick: () -> Unit ={}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 10.dp, end = 8.dp, bottom = 5.dp)
            .clickable { onCLick() },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.ic_bfit_logo_background),
        ),
        shape = RoundedCornerShape(20.dp),
        //elevation = 8.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (mealLabel, energy, serving, brand) = createRefs()

            Text(
                text = foodInfoModel.label,
                color = colorResource(id = R.color.blueForDarkGrey),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(mealLabel) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
            )

            Text(
                text = foodInfoModel.enercKcal,
                color = Color(0xFFFF5722),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(energy) {
                    start.linkTo(parent.start)
                    top.linkTo(brand.bottom, margin = 3.dp)
                    bottom.linkTo(parent.bottom, margin = 7.dp)
                }
            )

            Text(
                text = stringResource(R.string._100g),
                color = Color(0xFFFF5722),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(serving) {
                    start.linkTo(energy.end, margin = 8.dp)
                    top.linkTo(brand.bottom, margin = 3.dp)
                    bottom.linkTo(parent.bottom, margin = 7.dp)
                }
            )

            Text(
                text = foodInfoModel.brand,
                color = Color.Gray, // Replace with your color
                fontSize = 12.sp,
                modifier = Modifier.constrainAs(brand) {
                    start.linkTo(parent.start, margin = 14.dp)
                    top.linkTo(mealLabel.bottom, margin = 3.dp)
                }
            )
        }
    }
}
