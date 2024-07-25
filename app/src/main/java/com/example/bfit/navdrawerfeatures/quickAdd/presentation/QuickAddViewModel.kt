package com.example.bfit.navdrawerfeatures.quickAdd.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.navdrawerfeatures.quickAdd.domain.QuickAddUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickAddViewModel @Inject constructor(
    //private val quickAddRepository: QuickAddRepository
    private val quickAddUseCases: QuickAddUseCases
) : ViewModel() {
    var state by mutableStateOf(QuickAddState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: QuickAddEvent){
        when(event){
            is QuickAddEvent.CarbsChanged -> {
                state = state.copy(carbs = event.carbs)
            }
            is QuickAddEvent.FatChanged -> {
                state = state.copy(fat = event.fat)
            }
            is QuickAddEvent.FoodNameChanged -> {
                state = state.copy(foodName = event.foodName)
            }
            is QuickAddEvent.KcalChanged -> {
                state = state.copy(kcal = event.kcal)
            }
            is QuickAddEvent.MealChanged -> {
                state = state.copy(meal = event.meal)
            }
            is QuickAddEvent.ProteinChanged -> {
                state = state.copy(protein = event.protein)
            }
            is QuickAddEvent.ServingSizeChanged -> {
                state = state.copy(servingSize = event.servingSize)
            }
        }
    }
    fun validateData(data:String) : Boolean{
        return quickAddUseCases.validateData.execute(data)
    }
    fun validateInputData(
        context: Context,
        meal:String,
        kcal:String,
        protein:String,
        carb:String,
        fat:String,
        foodName:String,
        servingSize:String
    ): Boolean{
      return quickAddUseCases.validateInputData.execute(
          context = context,
          meal = meal,
          kcal = kcal,
          protein = protein,
          carb = carb,
          fat = fat,
          foodName = foodName,
          servingSize = servingSize
      )
    }

    private fun submitData(){
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Succes)
        }
    }

    sealed class ValidationEvent{
        object Succes: ValidationEvent()
    }
}