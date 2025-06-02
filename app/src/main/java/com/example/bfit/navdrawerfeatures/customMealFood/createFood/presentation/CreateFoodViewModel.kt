package com.example.bfit.navdrawerfeatures.customMealFood.createFood.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain.CreateFoodUseCases
import com.example.bfit.navdrawerfeatures.customMealFood.createFood.domain.repository.CreateFoodRepository
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddState
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddViewModel.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateFoodViewModel @Inject constructor (
    private val createFoodUseCases: CreateFoodUseCases,
    private val createFoodRepository: CreateFoodRepository
) : ViewModel(){

    var state by mutableStateOf(CreateFoodState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val foodMap: MutableMap<String, Any> = mutableMapOf()

    fun onEvent(event: CreateFoodEvent){
        when(event){
            is CreateFoodEvent.CarbsChanged -> state = state.copy(carbs = event.carbs)
            is CreateFoodEvent.FatChanged ->  state = state.copy(fat = event.fat)
            is CreateFoodEvent.FoodNameChanged -> state = state.copy(foodName = event.foodName)
            is CreateFoodEvent.KcalChanged ->  state = state.copy(kcal = event.kcal)
            is CreateFoodEvent.ProteinChanged -> state = state.copy(protein = event.protein)
            CreateFoodEvent.SubmitData -> {}
        }
    }
    fun validateData(data: String): Boolean {
        return createFoodUseCases.validateData.execute(data)
    }
    fun validateInputData(
        context: Context,
        meal: String,
        kcal: String,
        protein: String,
        carb: String,
        fat: String,
        foodName: String,
    ): Boolean {
        return createFoodUseCases.validateInputData.execute(
            context = context,
            kcal = kcal,
            protein = protein,
            carb = carb,
            fat = fat,
            foodName = foodName,
        )
    }
    private fun updateMap() {
        foodMap["kcal"] = round(state.kcal.toDouble())
        foodMap["protein"] = round(state.protein.toDouble())
        foodMap["carb"] = round(state.carbs.toDouble())
        foodMap["fat"] = round(state.fat.toDouble())
        foodMap["servingType"]= "food"
    }
    private fun submitData() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

}
