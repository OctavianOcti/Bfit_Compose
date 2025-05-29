package com.example.bfit.navdrawerfeatures.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bfit.R

@Composable
fun CalorieCard(
    modifier: Modifier = Modifier,
    caloriesProgress: Float = 0.7f,
    kcalGoal: String = "0 kcal",
    dailyKcal: String = "0 kcal",
    kcalLeft:String= "700"
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = modifier
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 25.dp)
            .background(
                brush = Brush.verticalGradient(listOf(Color.DarkGray, Color.Black)),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            val (
                title, progressBar, progressText, kcalLeftText,
                goalIcon, goalText, kcalGoalText,
                foodIcon, foodText, dailyKcalText
            ) = createRefs()

            Text(
                text = stringResource(R.string.calories),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.blueForDarkGrey),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

//            CircularProgressIndicator(
//                progress = { caloriesProgress },
//                modifier = Modifier
//                    .size(150.dp)
//                    .constrainAs(progressBar) {
//                        top.linkTo(parent.top, margin = 16.dp)
//                        end.linkTo(parent.end)
//                    },
//                strokeWidth = 8.dp,
//                trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
//                color = if (caloriesProgress == 0.0f) Color.Red else colorResource(R.color.blueForDarkGrey)
//            )
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(progressBar) {
                        top.linkTo(parent.top, margin = 16.dp)
                        end.linkTo(parent.end)
                    }
            ) {
                if (caloriesProgress == 0.0f) {
                    // Draw a full red circle as background
                    CircularProgressIndicator(
                        progress = { 1f },
                        strokeWidth = 8.dp,
                        color = colorResource(R.color.darkWhite),
                        modifier = Modifier.matchParentSize()
                    )
                }

                // Foreground progress indicator
                CircularProgressIndicator(
                    progress = { caloriesProgress },
                    strokeWidth = 8.dp,
                    color = colorResource(R.color.blueForDarkGrey),
                    trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                    modifier = Modifier.matchParentSize()
                )
            }


            Text(
                text = kcalLeft,
                fontSize = 22.sp,
                color =  if (kcalLeft == "0.0") colorResource(R.color.whiteDelimiter) else colorResource(R.color.orange) ,
                modifier = Modifier.constrainAs(progressText) {
                    centerTo(progressBar)
                }
            )

            Text(
                text = "kcal left",
                fontSize = 18.sp,
                color = colorResource(R.color.orange),
                modifier = Modifier.constrainAs(kcalLeftText) {
                    top.linkTo(progressText.bottom, margin = 8.dp)
                    start.linkTo(progressText.start)
                    end.linkTo(progressText.end)
                }
            )

            Image(
                painter = painterResource(R.drawable.nav_goals),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(goalIcon) {
                        top.linkTo(parent.top, margin = 50.dp)
                        start.linkTo(parent.start, margin = 20.dp)
                    },
                colorFilter = ColorFilter.tint(colorResource(R.color.whiteDelimiter))
            )

            Text(
                text = stringResource(R.string.calorie_goal),
                fontSize = 14.sp,
                color = colorResource(R.color.darkWhite),
                modifier = Modifier.constrainAs(goalText) {
                    start.linkTo(goalIcon.end, margin = 12.dp)
                    top.linkTo(goalIcon.top)
                }
            )

            Text(
                text = kcalGoal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE4AB38),
                modifier = Modifier.constrainAs(kcalGoalText) {
                    start.linkTo(goalText.start)
                    top.linkTo(goalText.bottom)
                    end.linkTo(goalText.end)
                }
            )

            Image(
                painter = painterResource(R.drawable.baseline_note_alt_24),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .constrainAs(foodIcon) {
                        top.linkTo(goalIcon.bottom, margin = 30.dp)
                        start.linkTo(goalIcon.start)
                        end.linkTo(goalIcon.end)
                    },
                colorFilter = ColorFilter.tint(Color(0xFF8BC34A))
            )

            Text(
                text = stringResource(R.string.food),
                fontSize = 14.sp,
                color = colorResource(R.color.darkWhite),
                modifier = Modifier.constrainAs(foodText) {
                    top.linkTo(foodIcon.top)
                    start.linkTo(goalText.start)
                }
            )

            Text(
                text = dailyKcal,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE4AB38),
                modifier = Modifier.constrainAs(dailyKcalText) {
                    bottom.linkTo(foodIcon.bottom)
                    start.linkTo(kcalGoalText.start)
                }
            )
        }
    }
}
