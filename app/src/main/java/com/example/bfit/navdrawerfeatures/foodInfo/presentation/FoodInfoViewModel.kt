package com.example.bfit.navdrawerfeatures.foodInfo.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.navdrawerfeatures.foodInfo.domain.FoodInfoUseCases
import com.example.bfit.navdrawerfeatures.foodInfo.domain.repository.FoodInfoRepository
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddViewModel.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodInfoViewModel @Inject constructor(
    private val foodInfoUseCases: FoodInfoUseCases
):ViewModel() {
    var state by mutableStateOf(FoodInfoState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: FoodInfoEvent){
        when(event){
            is FoodInfoEvent.CarbsChanged -> {
                state = state.copy(carbs = event.carbs)
            }
            is FoodInfoEvent.FatChanged -> {
                state = state.copy(fat = event.fat)
            }
            is FoodInfoEvent.KcalChanged -> {
                state = state.copy(kcal = event.kcal)
            }
            is FoodInfoEvent.MealChanged -> {
                state = state.copy(meal = event.meal)
            }
            is FoodInfoEvent.ProteinChanged -> {
                state = state.copy(protein = event.protein)
            }
            is FoodInfoEvent.ServingSizeChanged -> {
                state = state.copy(servingSize = event.servingSize)
            }

        }
    }


    fun validateServing(serving:String): Boolean{
        return foodInfoUseCases.validateServing.execute(serving)
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