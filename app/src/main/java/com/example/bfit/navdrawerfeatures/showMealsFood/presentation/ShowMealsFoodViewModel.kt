package com.example.bfit.navdrawerfeatures.showMealsFood.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository.ShowMealsFoodRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowMealsFoodViewModel @Inject constructor(
    private var showMealFoodRepository: ShowMealsFoodRepository
) : ViewModel() {

    var state by mutableStateOf(ShowMealsFoodState())
        private set
//    private val _foodInfoState = MutableStateFlow<Resource<List<FoodInfoModel>>>(Resource.Loading())
//    val foodInfoState: StateFlow<Resource<List<FoodInfoModel>>> = _foodInfoState
    private val _foodInfoState = MutableStateFlow<List<FoodInfoModel>>(emptyList())
    val foodInfoState: StateFlow<List<FoodInfoModel>> = _foodInfoState


    fun onEvent(event: ShowMealsFoodEvent) {
        when (event) {
            is ShowMealsFoodEvent.FormattedDateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
            }

            is ShowMealsFoodEvent.MealChanged -> {
                state = state.copy(meal = event.meal)
            }

            is ShowMealsFoodEvent.FoodChanged -> {
                getFood(DataProvider.user!!.uid, state.formattedDate, state.meal)
               // Log.d("FoodInfoState", foodInfoState.value.data.toString())

            }

            is ShowMealsFoodEvent.DeleteFood -> {
               // _foodInfoState.value = _foodInfoState.value.toMutableList().also { it.remove(event.food) }
               // _foodInfoState.value = _foodInfoState.value.toMutableList().apply {remove(event.food) }
                updateMacros(event.food)
                Log.d("FoodInfoState", _foodInfoState.value.toString())
            }
        }
    }
    private fun updateMacros(food:FoodInfoModel){
        viewModelScope.launch {
            val response = showMealFoodRepository.updateMacros(
                DataProvider.user!!.uid,
                state.formattedDate,
                food
            )
            when (response) {
                is Response.Failure -> {

                }

                Response.Loading -> {

                }

                is Response.Success -> {
                    deleteFoodFromDatabase(food)
                }
            }

        }
    }

    private fun deleteFoodFromDatabase(food:FoodInfoModel) {
        viewModelScope.launch {
            val response = showMealFoodRepository.deleteFoodFromDatabase(
                DataProvider.user!!.uid,
                state.formattedDate,
                food,
                state.meal
            )
            when (response) {
                is Response.Failure -> {

                }

                Response.Loading -> {

                }

                is Response.Success -> {
                    _foodInfoState.value = _foodInfoState.value.toMutableList().apply {remove(food) }
                }
            }
        }
    }


    private fun getFood(uid: String, formattedDate: String, meal: String) {
        viewModelScope.launch {
            showMealFoodRepository.getFood(uid, formattedDate, meal).collectLatest { resource ->
                when(resource){
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _foodInfoState.value = resource.data?.toList() ?: emptyList()
                        //Log.d("Test",_foodInfoState.value.data.toString())
                    }
                }

            }
        }
    }
}