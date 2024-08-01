package com.example.bfit.navdrawerfeatures.showMealsFood.presentation

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ShowMealFoodScreen(
    meal: String,
    formattedDate: String,
    navigateToDiary: () -> Unit = {},
    navigateToFoodInfo: (FoodInfoModel, String, String) -> Unit = { _, _, _ -> }
) {
    val viewModel: ShowMealsFoodViewModel = hiltViewModel()
    val foodInfoState by viewModel.foodInfoState.collectAsState()
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = meal, key2 = formattedDate) {
        viewModel.onEvent(ShowMealsFoodEvent.MealChanged(meal))
        viewModel.onEvent(ShowMealsFoodEvent.FormattedDateChanged(formattedDate))
        Log.d("ShowMealFoodScreen", "state.meal= ${state.meal} +  state.formattedDate=${state.formattedDate}")
    }
    LaunchedEffect(key1 = state.meal, key2 = state.formattedDate) {
        if (state.meal.isNotEmpty() && state.formattedDate.isNotEmpty()) {
            Log.d("ShowMealFoodScreen", "Am intrat")
            viewModel.onEvent(ShowMealsFoodEvent.FoodChanged)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = { navigateToDiary() }) {
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
                actions = {},
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.darkGrey))
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(8.dp)) // Add some space between the divider and the text

                Text(
                    text = "$meal ($formattedDate)",
                    color = colorResource(id = R.color.blueForDarkGrey),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp)) // Add space between the text and the LazyColumn

//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    items(foodInfoState.size) { index ->
//                        SwipeDismissItem(
//                            item = foodInfoState[index],
//                           // onRemove = { viewModel.removeFoodItem(foodInfoState[index]) },
//                            onRemove = {viewModel.onEvent(ShowMealsFoodEvent.DeleteFood(foodInfoState[index]))},
//                            modifier = Modifier.animateItemPlacement(tween(200))
//                        ) {
//                            MealCard(
//                                foodInfoModel = foodInfoState[index],
//                                onCLick = { navigateToFoodInfo(foodInfoState[index], state.meal, state.formattedDate) }
//                            )
//                        }
//                    }
//                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    items(
                        items = foodInfoState,
                        key = { it.label } // Assuming `id` is a unique identifier for each FoodInfoModel
                    ) { foodInfo ->
                        SwipeDismissItem(
                            item = foodInfo,
                            onRemove = { viewModel.onEvent(ShowMealsFoodEvent.DeleteFood(foodInfo)) },
                            modifier = Modifier.animateItemPlacement(tween(200))
                        ) {
                            MealCard(
                                foodInfoModel = foodInfo,
                                onCLick = { navigateToFoodInfo(foodInfo, state.meal, state.formattedDate) }
                            )
                        }
                    }
                }

            }

        }
    )
}


@Composable
fun SwipeDismissItem(
    item: FoodInfoModel,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.StartToEnd) {
                coroutineScope.launch {
                    delay(200)
                    onRemove()
                }
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissState,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                when (swipeToDismissState.currentValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> colorResource(id = R.color.darkGrey)
                }, label = "Animate by color"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            )
        },
        modifier = modifier
    ) {
        Card {
            content()
        }
//        HorizontalDivider()
    }
}




