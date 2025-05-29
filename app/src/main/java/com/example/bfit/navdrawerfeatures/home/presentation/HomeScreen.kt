package com.example.bfit.navdrawerfeatures.home.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bfit.R
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.util.presentation.DateSelectionRow
import com.example.bfit.util.Resource
import ir.ehsannarmani.compose_charts.models.Bars
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state
    val userInfoState = viewModel.profile

    val last7DaysKcalState by viewModel.last7DaysKcal.collectAsStateWithLifecycle()

    // Initialize today's date in the view model or any other logic if needed
    LaunchedEffect(key1 = state.formattedDate) {
        if (state.formattedDate.isEmpty()) {
            val todayDate = SimpleDateFormat("dd-MM-yyyy").format(Date())
            viewModel.onEvent(HomeEvents.DateChanged(todayDate))
            viewModel.loadLast7DaysKcal(todayDate)
            Log.d("todayDate",todayDate)
        }
    }
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.darkGrey))
                .fillMaxSize()
        ) {
            // Top bar - always visible, no Scaffold padding applied
            DateSelectionRow(
                formattedDate = state.formattedDate,
                onDateSelected = { formattedDate ->
                    viewModel.onEvent(
                        HomeEvents.DateChanged(formattedDate)
                    )
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NutrientsLayout(
                    userInfo = userInfoState.value,
                    dailyCalories = state.dailyCalories.toString(),
                    dailyProtein = state.dailyProtein.toString(),
                    dailyCarb = state.dailyCarb.toString(),
                    dailyFat = state.dailyFat.toString(),
                    caloriesProgress = state.caloriesProgress,
                    proteinProgress = state.proteinProgress,
                    carbProgress = state.carbsProgress,
                    fatProgress = state.fatProgress,
                    kcalLeft = state.kcalLeft.toString()
                )
                Spacer(Modifier.height(50.dp))
                //HistoryChart()
                when (last7DaysKcalState) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is Resource.Success -> {
                        val data = (last7DaysKcalState as Resource.Success).data
                        val barsData = data?.map { entry ->
                            Bars(
                                label = entry.dateLabel,  // date label like "28-05"
                                values = listOf(
                                    Bars.Data(
                                        label = "Calories",
                                        value = entry.totalKcal.toDoubleOrNull() ?: 0.0,
                                        color = Brush.verticalGradient(
                                            listOf(
                                                colorResource(R.color.blueForDarkGrey),
                                                colorResource(R.color.gradientLightBlue)
                                            )
                                        )
                                    )
                                )
                            )
                        }

                        if (barsData != null) {
                            HistoryChart(data = barsData)
                        }
                    }
                    is Resource.Error -> {
                        Text(
                            text = "Error loading chart data",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                BarChartLegendText()

            }
        }
}


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NutrientsLayout(
    userInfo: UserInfo,
    dailyCalories:String,
    dailyProtein:String,
    dailyCarb:String,
    dailyFat:String,
    caloriesProgress:Float,
    proteinProgress:Float,
    carbProgress:Float,
    fatProgress:Float,
    kcalLeft:String
) {
    val tabTitles = listOf("Calories", "Macros",)
    val pagerState = rememberPagerState { tabTitles.size }
    val coroutineScope = rememberCoroutineScope()
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) { page ->
            when (page) {
                0 -> CalorieCard(
                    modifier = Modifier.height(300.dp),
                    caloriesProgress = caloriesProgress,
                    kcalGoal = userInfo.getCalories().toString()+ " kcal",
                    dailyKcal = "$dailyCalories kcal",
                    kcalLeft = kcalLeft
                )
                1 -> MacrosCard(
                    modifier = Modifier.height(300.dp),
                    protein = "${dailyProtein}/"+userInfo.getProtein().toString()+"g",
                    carbs = "${dailyCarb}/"+userInfo.getCarb().toString()+"g",
                    fat= "${dailyFat}/"+userInfo.getFat().toString()+"g",
                    proteinProgress= proteinProgress,
                    carbProgress=carbProgress,
                    fatProgress=fatProgress
                )
            }
        }

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

    }
}
@Composable
fun BarChartLegendText() {
    Text(
        text = stringResource(R.string.last_7_days_tracked_calories),
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = Color(0xFFE4AB38),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
}

