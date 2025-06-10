package com.example.bfit.navdrawerfeatures.customMealFood.meal.createMeal.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.presentation.CreateFoodState
import com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.presentation.CreateFoodViewModel.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateMealViewModel @Inject constructor(): ViewModel() {
    var state by mutableStateOf(CreateMealState())
        private set
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: CreateMealEvent){
        when(event){
            CreateMealEvent.AddMeal -> {}
            is CreateMealEvent.CarbsChanged -> state = state.copy(carbs = event.carbs)
            is CreateMealEvent.FatChanged -> state = state.copy(fat = event.fat)
            is CreateMealEvent.KcalChanged -> state = state.copy(kcal = event.kcal)
            is CreateMealEvent.MealNameChanged -> state = state.copy(mealName = event.mealName)
            is CreateMealEvent.ProteinChanged -> state = state.copy(protein = event.protein)
            CreateMealEvent.SubmitData -> {}
        }
    }
    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}