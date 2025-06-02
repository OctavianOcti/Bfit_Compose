package com.example.bfit.navdrawerfeatures.customMealFood.common.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.customMealFood.viewFood.presentation.FoodScreen
import com.example.bfit.navdrawerfeatures.customMealFood.viewMeals.presentation.MealsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomMealFood(
    navigateToMain: () -> Unit = {},
    navigateToCreateFood: () ->Unit ={}
){
    Scaffold(
     topBar = {
         TopAppBar(
             title= { Text(text = "Custom Meal/Food") },
             navigationIcon = {
                 IconButton(onClick = {navigateToMain()}) {
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
         )
     },
       content =  {paddingValues ->
           Column(
               modifier = Modifier
                   .fillMaxSize()
                   //.verticalScroll(rememberScrollState())
                   .padding(paddingValues)
                   .background(colorResource(id= R.color.darkGrey))

           ){
            FoodMealsLayout(navigateToCreateFood={navigateToCreateFood()})
           }
       }
    )
}
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun FoodMealsLayout(
    navigateToCreateFood: () -> Unit
) {
    val tabTitles = listOf("Food", "Meals",)
    val pagerState = rememberPagerState { tabTitles.size }
    val coroutineScope = rememberCoroutineScope()
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = colorResource(R.color.darkGrey),
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = 24.dp)
                        .height(3.dp),
                    color = colorResource(R.color.darkGrey) // optional: indicator color
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(title) },
                    selectedContentColor = Color.Gray,
                    unselectedContentColor = Color.DarkGray
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()

        ) { page ->
            when (page) {
                0 -> FoodScreen(navigateToCreateFood = navigateToCreateFood)
                1 -> MealsScreen()
            }
        }
    }
}