package com.example.bfit.navdrawerfeatures.diary.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    navigateToMain: () -> Unit = {},
    navigateToAddFood: (String) -> Unit = {},
    navigateToShowMealsFoodScreen : (String, String) -> Unit = { _, _ ->}
) {
    val viewModel: DiaryViewModel = hiltViewModel()
    val state = viewModel.state
    val context = LocalContext.current

    // Initialize today's date in the view model or any other logic if needed
    LaunchedEffect(key1 = state.formattedDate) {
        if (state.formattedDate.isEmpty()) {
            val todayDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
            viewModel.onEvent(DiaryEvents.DateChanged(todayDate))
            Log.d("todayDate",todayDate)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { navigateToMain() }) {
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
                    state = state,
                    onDateSelected = { formattedDate ->
                        viewModel.onEvent(
                            DiaryEvents.DateChanged(
                                formattedDate
                            )
                        )
                    }
                )
                MainScreen(state,navigateToAddFood,navigateToShowMealsFoodScreen)
            }
        }
    )
}
@Composable
fun MealCard(
    mealType: String,
    mealImageRes: Int,
    onAddClick: () -> Unit,
    onCardClick: () -> Unit,
    message: String
) {
    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    fun shake() {
        coroutineScope.launch {
            shakeOffset.animateTo(
                targetValue = 16f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = -16f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = 8f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = -8f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, top = 12.dp, end = 18.dp, bottom = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = R.color.ic_bfit_logo_background))
            .graphicsLayer {
                translationX = shakeOffset.value
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        shake()
                        onCardClick()
                    }
                )
            }
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
                onClick = {
                    shake()
                    onAddClick()
                },
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
fun MainScreen(
    state: DiaryState,
    navigateToAddFood: (String) -> Unit,
    navigateToShowMealsFoodScreen: (String, String) -> Unit
) {
    Column {
        MealCard(
            mealType = stringResource(id = R.string.breakfast),
            mealImageRes = R.drawable.breakfast,
            onAddClick = { navigateToAddFood(state.formattedDate) },
            onCardClick = { navigateToShowMealsFoodScreen("Breakfast", state.formattedDate) },
            message = if (state.mealTexts.isNotEmpty()) {
                state.mealTexts[0]
            } else {
                stringResource(id = R.string.no_tracked_food_available)
            }
        )

        MealCard(
            mealType = stringResource(id = R.string.lunch),
            mealImageRes = R.drawable.lunch,
            onAddClick = { navigateToAddFood(state.formattedDate)},
            onCardClick = { navigateToShowMealsFoodScreen("Lunch", state.formattedDate) },
            message = if (state.mealTexts.isNotEmpty()) {
                state.mealTexts[1]
            } else {
                stringResource(id = R.string.no_tracked_food_available)
            }
        )

        MealCard(
            mealType = stringResource(id = R.string.dinner),
            mealImageRes = R.drawable.dinner,
            onAddClick = { navigateToAddFood(state.formattedDate) },
            onCardClick = { navigateToShowMealsFoodScreen("Dinner", state.formattedDate) },
            message = if (state.mealTexts.isNotEmpty()) {
                state.mealTexts[2]
            } else {
                stringResource(id = R.string.no_tracked_food_available)
            }
        )

        MealCard(
            mealType = stringResource(id = R.string.snacks),
            mealImageRes = R.drawable.snacks,
            onAddClick = { navigateToAddFood(state.formattedDate) },
            onCardClick = { navigateToShowMealsFoodScreen("Snacks", state.formattedDate) },
            message = if (state.mealTexts.isNotEmpty()) {
                state.mealTexts[3]
            } else {
                stringResource(id = R.string.no_tracked_food_available)
            }
        )
    }
}