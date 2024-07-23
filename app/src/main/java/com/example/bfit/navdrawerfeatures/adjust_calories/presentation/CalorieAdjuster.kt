package com.example.bfit.navdrawerfeatures.adjust_calories.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bfit.R
import kotlinx.coroutines.launch

@Composable
fun CalorieAdjuster(
    state: MacrosState,
    onShowInputDialog: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val shakeOffset = remember { Animatable(0f) }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.ic_bfit_logo_background))
            .padding(vertical = 10.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    coroutineScope.launch {
                        shakeOffset.animateTo(
                            targetValue = 15f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = -15f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = 10f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = -10f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 50)
                        )
                    }
                    onShowInputDialog("Enter calorie amount:")
                })
            }
            .offset(x = shakeOffset.value.dp)
    ) {
        val (numberText, unitText, descriptionText) = createRefs()

        Text(
            text = state.calories,
            color = colorResource(id = R.color.blueForDarkGrey),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(numberText) {
                start.linkTo(parent.start, margin = 20.dp)
                top.linkTo(parent.top)
            }
        )
        Text(
            text = "kcal",
            color = colorResource(id = R.color.blueForDarkGrey),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(unitText) {
                start.linkTo(numberText.end, margin = 4.dp) // Add margin between number and kcal
                top.linkTo(numberText.top)
                bottom.linkTo(numberText.bottom)
            }
        )
        Text(
            text = "Adjust your default calories goal",
            color = colorResource(id = R.color.whiteDelimiter),
            fontSize = 18.sp,
            modifier = Modifier.constrainAs(descriptionText) {
                start.linkTo(parent.start, margin = 20.dp)
                top.linkTo(numberText.bottom, margin = 0.dp)
            }
        )
    }
}