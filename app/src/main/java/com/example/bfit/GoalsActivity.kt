package com.example.bfit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun YourComposable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(colorResource(id = R.color.darkGrey))
    ) {
        ToolbarWithIcon()

        Text(
            text = stringResource(id = R.string.enter_your_stats_below),
            color = Color(0xFFFF5722),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        RelativeLayoutWithFields()

        RelativeLayoutForGoals()

        RelativeLayoutForTips()

        CenteredImage()
    }
}

@Composable
fun ToolbarWithIcon() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color(0xFF37474F)),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_check_24),
            contentDescription = stringResource(id = R.string.imgconfirm),
            modifier = Modifier
                .padding(end = 10.dp)
                .size(24.dp)
        )
    }
}

@Composable
fun RelativeLayoutWithFields() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 25.dp)
            .background(color = Color(0xFF4E4E4E))
    ) {
        FieldRow(stringResource(id = R.string.gender), stringResource(id = R.string.set_gender))
        Divider()
        FieldRow(stringResource(id = R.string.weight_kgs), stringResource(id = R.string.set_current_weight))
        Divider()
        FieldRow(stringResource(id = R.string.height_cm), stringResource(id = R.string.set_current_height))
        Divider()
        FieldRow(stringResource(id = R.string.age_years), stringResource(id = R.string.set_current_age))
        Divider()
        FieldRow(stringResource(id = R.string.activity_level), stringResource(id = R.string.set_current_activity_level))
        Divider()
        FieldRow(stringResource(id = R.string.goal), stringResource(id = R.string.set_your_goal))
    }
}

@Composable
fun RelativeLayoutForGoals() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.nutrition_goals),
            color = Color(0xFFFFFFFF),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 2.dp)
        )
        Divider(color = Color(0xFFFFFFFF))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF37474F))
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.calorie_carbs_protein_and_fat_goals),
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.adjust_your_default_or_daily_goals),
                color = Color(0xFFB0BEC5),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.you_should_set_your_personal_information_first),
                color = Color(0xFF4CAF50),
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun RelativeLayoutForTips() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.GoalsTip),
            color = Color(0xFFF17673),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun CenteredImage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo_app),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)

        )
    }
}

@Composable
fun FieldRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 15.dp)
    ) {
        Text(
            text = label,
            color = Color(0xFFFFFFFF),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color = Color(0xFF42A5F5),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun Divider(color: Color = Color(0xFFFFFFFF)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
            .padding(top = 10.dp)
    )
}