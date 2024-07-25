package com.example.bfit.navdrawerfeatures.showMealsFood

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowMealFoodScreen(
    meal: String,
    formattedDate: String,
    navigateToDiary: () -> Unit = {}
) {
    val viewModel: ShowMealsFoodViewModel = hiltViewModel()
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = meal, formattedDate) {
        viewModel.onEvent(ShowMealsFoodEvent.MealChanged(meal))
        viewModel.onEvent(ShowMealsFoodEvent.FormattedDateChanged(formattedDate))
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
                    items(3) { // Assuming you want to display 3 MealCards
                        MealCard()
                    }
                }
            }
        }
    )
}
