package com.example.bfit.navdrawerfeatures.adjust_calories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.bfit.R

@Composable
fun MacroAdjuster(
    state: MacrosState,
    viewModel: AdjustMacrosViewModel,
    proteinPercentage: Float,
    carbPercentage: Float,
    fatPercentage: Float,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.darkGrey))
            .padding(start = 25.dp, top = 60.dp, end = 25.dp, bottom = 16.dp)
    ) {
        val (llProtein, sliderProtein, llCarb, sliderCarb, llFat, sliderFat) = createRefs()

        // Protein LinearLayout
        Row(
            modifier = Modifier
                .constrainAs(llProtein) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
        ) {
            Text(
                text = "Protein",
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp
            )
            Text(
                text = "${state.protein}g",
                color = colorResource(id = R.color.orange),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "${state.proteinPercentage}%",
                color = Color(0xFF4CAF50),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        // Protein Slider
        Slider(
            value = proteinPercentage,
            onValueChange = { newValue ->
                viewModel.onEvent(
                    MacrosEvent.ProteinChanged(
                        (state.calories.toFloat() * newValue / 400).toInt().toString()
                    )
                )
                viewModel.onEvent(
                    MacrosEvent.ProteinPercentageChanged(
                        newValue.toInt().toString()
                    )
                )
            },
            valueRange = 0f..100f,
            steps = 100,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.purple_500),
                inactiveTrackColor = Color(0xFFE4AB38),
            ),
            modifier = Modifier
                .constrainAs(sliderProtein) {
                    top.linkTo(llProtein.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Carb LinearLayout
        Row(
            modifier = Modifier
                .constrainAs(llCarb) {
                    top.linkTo(sliderProtein.bottom, margin = 70.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
        ) {
            Text(
                text = "Carb",
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp
            )
            Text(
                text = "${state.carb}g",
                color = colorResource(id = R.color.orange),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "${state.carbPercentage}%",
                color = Color(0xFF4CAF50),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        // Carb Slider
        Slider(
            value = carbPercentage,
            onValueChange = { newValue ->
                viewModel.onEvent(
                    MacrosEvent.CarbPercentageChanged(
                        newValue.toInt().toString()
                    )
                )
                viewModel.onEvent(
                    MacrosEvent.CarbChanged(
                        (state.calories.toFloat() * newValue / 400).toInt().toString()
                    )
                )
            },
            valueRange = 0f..100f,
            steps = 100,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.purple_500),
                inactiveTrackColor = Color(0xFFE4AB38),
            ),
            modifier = Modifier
                .constrainAs(sliderCarb) {
                    top.linkTo(llCarb.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        // Fat LinearLayout
        Row(
            modifier = Modifier
                .constrainAs(llFat) {
                    top.linkTo(sliderCarb.bottom, margin = 70.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
        ) {
            Text(
                text = "Fat",
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp
            )
            Text(
                text = "${state.fat}g",
                color = colorResource(id = R.color.orange),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            Text(
                text = "${state.fatPercentage}%",
                color = Color(0xFF4CAF50),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        // Fat Slider
        Slider(
            value = fatPercentage,
            onValueChange = { newValue ->
                viewModel.onEvent(
                    MacrosEvent.FatPercentageChanged(
                        newValue.toInt().toString()
                    )
                )
                viewModel.onEvent(
                    MacrosEvent.FatChanged(
                        (state.calories.toFloat() * newValue / 900).toInt().toString()
                    )
                )
            },
            valueRange = 0f..100f,
            steps = 100,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.purple_500),
                inactiveTrackColor = Color(0xFFE4AB38),
            ),
            modifier = Modifier
                .constrainAs(sliderFat) {
                    top.linkTo(llFat.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}