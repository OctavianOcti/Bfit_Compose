package com.example.bfit.navdrawerfeatures.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R

@Composable
fun MacrosCard(
    modifier: Modifier = Modifier,
    protein: String = "0/0g",
    carbs: String = "0/0g",
    fat: String = "0/0g",
    proteinProgress:Float = 70f,
    carbProgress:Float = 50f,
    fatProgress:Float = 30f

) {
    Column(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 25.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color.DarkGray, Color.Black)
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Macronutrients",
            color = Color(0xFF00BFFF), // Replace with colorResource(R.color.blueForDarkGrey) if using MaterialTheme
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

        MacronutrientRow("Protein:", protein,proteinProgress)
        Spacer(modifier = Modifier.height(20.dp))
        MacronutrientRow("Carbs:", carbs,carbProgress)
        Spacer(modifier = Modifier.height(20.dp))
        MacronutrientRow("Fat:", fat,fatProgress)
    }
}

@Composable
fun MacronutrientRow(label: String, value: String,progress:Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(0.3f)
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp)),
            strokeCap = StrokeCap.Round,
            gapSize = (-15).dp,
            drawStopIndicator = {},
            color = colorResource(R.color.gradientLightBlue)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Row(
            modifier = Modifier.weight(0.4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis

            )
            Text(
                text = value,
                color = Color(0xFFE4AB38),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}