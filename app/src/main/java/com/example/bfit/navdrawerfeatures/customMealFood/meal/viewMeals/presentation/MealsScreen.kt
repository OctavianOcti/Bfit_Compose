package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.customMealFood.common.presentation.FoodMealSection
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.presentation.FoodCard
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.presentation.SwipeDismissItem
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.presentation.ViewFoodEvent
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.MealInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MealsScreen(
    navigateToCreateMeal : () -> Unit = {}
){
    val viewModel: CustomMealsViewModel = hiltViewModel()
    val mealInfoState by viewModel.mealInfoState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        FoodMealSection(
            onClick = {navigateToCreateMeal()},
            text = stringResource(R.string.create_a_meal),
            painter = painterResource(id = R.drawable.watermelon),
            imageContentDescription = stringResource(R.string.create_a_food)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(
                items = mealInfoState,
                key = { it.label }
            ) { mealInfo ->
                SwipeDismissItem(
                    item = mealInfo,
                    onRemove = { viewModel.onEvent(ViewMealsEvent.DeleteMeals(mealInfo)) },
                ) {
                    MealCard(
                        mealInfoModel = mealInfo,
                        onCLick = { }
                    )
                }
            }
        }
    }
}
@Composable
fun SwipeDismissItem(
    item: MealInfoModel,
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