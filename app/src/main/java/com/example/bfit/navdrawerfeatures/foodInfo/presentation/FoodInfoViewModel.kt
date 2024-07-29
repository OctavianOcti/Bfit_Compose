package com.example.bfit.navdrawerfeatures.foodInfo.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.foodInfo.domain.FoodInfoUseCases
import com.example.bfit.navdrawerfeatures.foodInfo.domain.PreviousMacros
import com.example.bfit.navdrawerfeatures.foodInfo.domain.repository.FoodInfoRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodInfoViewModel @Inject constructor(
    private val foodInfoUseCases: FoodInfoUseCases,
    private val foodInfoRepository: FoodInfoRepository
) : ViewModel() {
    var state by mutableStateOf(FoodInfoState())
        private set
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    private val dayInfoMap: MutableMap<String, Any> = mutableMapOf()
    private val foodMap: MutableMap<String, Any> = mutableMapOf()

    fun onEvent(event: FoodInfoEvent) {
        when (event) {
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

            is FoodInfoEvent.FoodBrandChanged -> {
                state = state.copy(foodBrand = event.foodBrand)
            }

            is FoodInfoEvent.FoodNameChanged -> {
                state = state.copy(foodName = event.foodName)
            }

            is FoodInfoEvent.PreviousServingSizeChanged -> {
                state = state.copy(previousServingSize = event.previousServingSize)
            }

            is FoodInfoEvent.PreviousFoodCarbChanged -> {
                state = state.copy(previousFoodCarb = event.previousFoodCarb)
            }

            is FoodInfoEvent.PreviousFoodFatChanged -> {
                state = state.copy(previousFoodFat = event.previousFoodFat)
            }

            is FoodInfoEvent.PreviousFoodKcalChanged -> {
                state = state.copy(previousFoodKcal = event.previousFoodKcal)
            }

            is FoodInfoEvent.PreviousFoodProteinChanged -> {
                state = state.copy(previousFoodProtein = event.previousFoodProtein)
            }

            is FoodInfoEvent.MacrosChanged -> {
                state = state.copy(servingSize = event.servingSize)
                updateMacrosValues()
            }

            FoodInfoEvent.SubmitData -> {
                Log.d("SubmitData", "Am intrat")
                setMacros()
            }

            is FoodInfoEvent.FormattedDateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
            }
        }
    }

    private fun setMacros() {
        viewModelScope.launch {
            updateMaps()
            Log.d("state.formatteddate", state.formattedDate)
            foodInfoRepository.getPreviousTotalMacros(
                DataProvider.user!!.uid,
                state.formattedDate
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        Log.d("Eroare", result.message.toString())
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                       updateDaysDocument(result)
                    }
                }
            }.launchIn(this)
        }
    }

    private suspend fun updateDaysDocument(result: Resource<PreviousMacros>){
        Log.d("PreviousTotalKcal", result.data?.previousTotalKcal.toString())
        val response = foodInfoRepository.updateDaysDocument(
            DataProvider.user!!.uid,
            state.formattedDate,
            dayInfoMap,
            foodMap,
            state.kcal,
            state.protein,
            state.carbs,
            state.fat,
            result.data?.previousTotalKcal.toString(),
            result.data?.previousTotalProtein.toString(),
            result.data?.previousTotalCarb.toString(),
            result.data?.previousTotalFat.toString(),
            state.previousFoodKcal,
            state.previousFoodProtein,
            state.previousFoodCarb,
            state.previousFoodFat,
            state.meal,
            state.foodName
        )
        when(response){
            is Response.Failure -> {}
            Response.Loading -> {}
            is Response.Success -> {validationEventChannel.send(ValidationEvent.Succes)}
        }
    }

    fun validateServing(serving: String): Boolean {
        return foodInfoUseCases.validateServing.execute(serving)
    }

    private fun updateMaps() {
        dayInfoMap["total_kcal"] = state.kcal
        dayInfoMap["total_protein"] = state.protein
        dayInfoMap["total_carb"] = state.carbs
        dayInfoMap["total_fat"] = state.fat

        foodMap["kcal"] = round(state.kcal.toDouble())
        foodMap["protein"] = round(state.protein.toDouble())
        foodMap["carb"] = round(state.carbs.toDouble())
        foodMap["fat"] = round(state.fat.toDouble())
        foodMap["serving_size"] = round(state.servingSize.toDouble())
        if (state.foodBrand.isNotEmpty()) foodMap["brand"] = state.foodBrand
    }

    private fun updateMacrosValues() {
        this.onEvent(FoodInfoEvent.ServingSizeChanged((round(state.servingSize.toDouble()).toString())))
        this.onEvent(FoodInfoEvent.KcalChanged(round(state.servingSize.toDouble() * state.kcal.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(FoodInfoEvent.ProteinChanged(round(state.servingSize.toDouble() * state.protein.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(FoodInfoEvent.CarbsChanged(round(state.servingSize.toDouble() * state.carbs.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(FoodInfoEvent.FatChanged(round(state.servingSize.toDouble() * state.fat.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(FoodInfoEvent.PreviousServingSizeChanged(state.servingSize))
    }


    private fun submitData() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Succes)
        }
    }

    sealed class ValidationEvent {
        object Succes : ValidationEvent()
    }

}