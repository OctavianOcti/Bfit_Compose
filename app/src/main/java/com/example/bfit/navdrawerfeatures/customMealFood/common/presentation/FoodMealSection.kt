package com.example.bfit.navdrawerfeatures.customMealFood.common.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.addFood.presentation.shakeAnimation

@Composable
fun FoodMealSection(
    onClick: () -> Unit = {},
    painter: Painter,
    text: String,
    imageContentDescription:String

) {
    var isShaking by remember { mutableStateOf(false) }
    val shakeModifier = shakeAnimation(
        modifier = Modifier,
        onShake = {
            isShaking = true
            onClick()
        }
    )
    val clickableModifier = Modifier
        .clickable {
            isShaking = true
        }
        .then(shakeModifier)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = clickableModifier
            .padding(start = 15.dp, top = 12.dp, bottom = 5.dp, end = 15.dp)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.ic_bfit_logo_background),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painter,
            contentDescription = imageContentDescription,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp
            )
        }
    }
}