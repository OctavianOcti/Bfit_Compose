package com.example.bfit.navdrawerfeatures.diary.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.util.presentation.DateSelectionRow
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
                    formattedDate = state.formattedDate,
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