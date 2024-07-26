package com.example.bfit.navdrawerfeatures.showMealsFood.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMealFoodScreen(
    meal: String,
    formattedDate: String,
    navigateToDiary: () -> Unit = {},
    navigateToFoodInfo: (FoodInfoModel,String,String)-> Unit = {_,_,_,->}
) {
    val viewModel: ShowMealsFoodViewModel = hiltViewModel()
    val foodInfoState by viewModel.foodInfoState.collectAsState()
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = meal, key2=formattedDate) {
        viewModel.onEvent(ShowMealsFoodEvent.MealChanged(meal))
        viewModel.onEvent(ShowMealsFoodEvent.FormattedDateChanged(formattedDate))
        Log.d("ShowMealsFoodScreen","state.meal= ${state.meal} +  state.formattedDate=${state.formattedDate}")

    }
    LaunchedEffect(key1 = state.meal, key2=state.formattedDate) {
        if(state.meal.isNotEmpty() && state.formattedDate.isNotEmpty()) {
            Log.d("ShowMealsFoodScreen","Am intrat")
            viewModel.onEvent(ShowMealsFoodEvent.FoodChanged)
            Log.d("ShowMealsFoodScreen", foodInfoState.data?.get(0)?.servingType.toString())
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    foodInfoState.data?.let {
                        items(it.size) { index ->
                            MealCard(
                                foodInfoModel = foodInfoState.data!![index],
                                onCLick = { navigateToFoodInfo(foodInfoState.data!![index],state.meal,state.formattedDate) }
                            )
                        }
                    }
                }
            }
        }
    )
}
