package com.example.bfit.navdrawerfeatures.addFood.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.navdrawerfeatures.addFood.domain.use_case.AddFoodUseCases
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val addFoodUseCases: AddFoodUseCases
) : ViewModel() {
    private val _foodDetailState = mutableStateOf(FoodDetailState())

    var state by mutableStateOf(AddFoodState())
        private set

    private val _foodInfoState = MutableStateFlow<List<FoodInfoModel>>(emptyList())
    val foodInfoState: StateFlow<List<FoodInfoModel>> = _foodInfoState

    fun onEvent(event: AddFoodEvent) {
        when (event) {
            is AddFoodEvent.SearchForFood -> {
                _foodInfoState.value = emptyList()
                _foodDetailState.value = FoodDetailState()
                if (state.text.isNotEmpty()) {
                    getFood(event.food)
                }
            }
            is AddFoodEvent.SearchTextChanged -> {
                state = state.copy(text = event.text)

            }

            is AddFoodEvent.SearchByBarcode -> {
                _foodInfoState.value= emptyList()
                _foodDetailState.value= FoodDetailState()
                    getFoodByBarcode(event.upc)

            }
        }
    }

    private fun getFood(food: String) {
        addFoodUseCases.getFoodUseCase(food).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _foodDetailState.value =
                        FoodDetailState(error = result.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
                    _foodDetailState.value = FoodDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    Log.d("AddFoodViewModel result", result.data.toString())
                    result.data?.hints?.let { hints ->
                        for (hint in hints) {
                            val label = hint.food.label ?: ""
                            val brand = hint.food.brand ?: ""
                            val energyKcal = hint.food.nutrients?.enercKcal?.let { round(it).toString() } ?: ""
                            val protein = hint.food.nutrients?.procnt?.let { round(it).toString() } ?: ""
                            val fat = hint.food.nutrients?.fat?.let { round(it).toString() } ?: ""
                            val carbs = hint.food.nutrients?.chocdf?.let { round(it).toString() } ?: ""
                            val fibers = hint.food.nutrients?.fibtg?.let { round(it).toString() } ?: ""
                            _foodInfoState.value += FoodInfoModel(
                                label,
                                brand,
                                energyKcal,
                                protein,
                                fat,
                                carbs,
                                fibers,
                                "",
                                ""
                            )
                        }
                    }
                    _foodDetailState.value = FoodDetailState(foodDetail = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getFoodByBarcode(upc: String) {
        addFoodUseCases.getFoodByBarcodeUseCase(upc).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _foodDetailState.value =
                        FoodDetailState(error = result.message ?: "An unexpected error occured")
                }

                is Resource.Loading -> {
                    _foodDetailState.value = FoodDetailState(isLoading = true)
                }

                is Resource.Success -> {
                    Log.d("AddFoodViewModel result", result.data.toString())
                    result.data?.hints?.let { hints ->
                        for (hint in hints) {
                            val label = hint.food.label ?: ""
                            val brand = hint.food.brand ?: ""
                            val energyKcal = hint.food.nutrients?.enercKcal?.let { round(it).toString() } ?: ""
                            val protein = hint.food.nutrients?.procnt?.let { round(it).toString() } ?: ""
                            val fat = hint.food.nutrients?.fat?.let { round(it).toString() } ?: ""
                            val carbs = hint.food.nutrients?.chocdf?.let { round(it).toString() } ?: ""
                            val fibers = hint.food.nutrients?.fibtg?.let { round(it).toString() } ?: ""
                            _foodInfoState.value += FoodInfoModel(
                                label,
                                brand,
                                energyKcal,
                                protein,
                                fat,
                                carbs,
                                fibers,
                                "",
                                ""
                            )
                        }
                    }
                    _foodDetailState.value = FoodDetailState(foodDetail = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}