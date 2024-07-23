package com.example.bfit.navdrawerfeatures.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun DiaryScreen(
    navigateToMain: ()->Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateToMain()}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "NavigateToMain"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.ic_bfit_logo_background),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {

                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(id = R.color.darkGrey))
                    .padding(paddingValues)
            ) {
               DateSelectionRow(
                   previousDateClick = { /*TODO*/ },
                   nextDateClick = { /*TODO*/ },
                   dateText = "Select Date"
               )
                MainScreen()
            }
        }
    )

}

@Composable
fun DateSelectionRow(
    previousDateClick: () -> Unit,
    nextDateClick: () -> Unit,
    dateText: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp), // Adjust padding as needed
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = previousDateClick,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.darkGrey)),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.PreviousDateButtonText),
                color = Color(0xFFFF5722),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }

        Text(
            text = dateText,
            color = colorResource(id = R.color.darkWhite),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Button(
            onClick = nextDateClick,
            colors = ButtonDefaults.buttonColors( colorResource(id = R.color.darkGrey)),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.NextDayButtonText),
                color = Color(0xFFFF5722),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MealCard(
    mealType: String,
    mealImageRes: Int,
    onAddClick: () -> Unit,
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, top = 12.dp, end = 18.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = R.color.ic_bfit_logo_background))
            //.background(painterResource(id = R.drawable.relative_layout_bg))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 25.dp)
        ) {
            Image(
                painter = painterResource(id = mealImageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
            )

            Spacer(modifier = Modifier.width(21.dp))

            Text(
                text = mealType,
                color = colorResource(id = R.color.blueForDarkGrey),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onAddClick,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.darkGrey)),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(end = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_food),
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = message,
            color = colorResource(id = R.color.whiteDelimiter),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        )
    }
}

@Composable
fun MainScreen() {
    Column {
        MealCard(
            mealType = stringResource(id = R.string.breakfast),
            mealImageRes = R.drawable.breakfast,
            onAddClick = { /* Handle add breakfast click */ },
            message = stringResource(id = R.string.tap_to_view_your_tracked_food)
        )

        MealCard(
            mealType = stringResource(id = R.string.lunch),
            mealImageRes = R.drawable.lunch,
            onAddClick = { /* Handle add lunch click */ },
            message = stringResource(id = R.string.tap_to_view_your_tracked_food)
        )

        MealCard(
            mealType = stringResource(id = R.string.dinner),
            mealImageRes = R.drawable.dinner,
            onAddClick = { /* Handle add dinner click */ },
            message = stringResource(id = R.string.tap_to_view_your_tracked_food)
        )

        MealCard(
            mealType = stringResource(id = R.string.snacks),
            mealImageRes = R.drawable.snacks,
            onAddClick = { /* Handle add snacks click */ },
            message = stringResource(id = R.string.tap_to_view_your_tracked_food)
        )
    }
}


