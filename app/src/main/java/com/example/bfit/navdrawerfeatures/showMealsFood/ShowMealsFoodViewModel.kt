package com.example.bfit.navdrawerfeatures.showMealsFood

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowMealsFoodViewModel @Inject constructor(

): ViewModel() {
    var state by mutableStateOf(ShowMealsFoodState())
        private set

    fun onEvent(event: ShowMealsFoodEvent){
        when (event){
            is ShowMealsFoodEvent.FormattedDateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
            }
            is ShowMealsFoodEvent.MealChanged -> {
                state = state.copy(meal = event.meal)
            }
        }
    }
}