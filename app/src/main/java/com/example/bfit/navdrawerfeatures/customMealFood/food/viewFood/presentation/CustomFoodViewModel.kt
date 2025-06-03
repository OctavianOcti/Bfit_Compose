package com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.domain.CustomFoodRepository
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomFoodViewModel @Inject constructor(
    private var customFoodRepository: CustomFoodRepository
) :ViewModel() {
    private val _foodInfoState = MutableStateFlow<List<FoodInfoModel>>(emptyList())
    val foodInfoState: StateFlow<List<FoodInfoModel>> = _foodInfoState
    fun onEvent(event: ViewFoodEvent) {
        when (event) {
            is ViewFoodEvent.DeleteFood -> deleteFoodFromDatabase(event.food)
        }
    }

    init{
        viewModelScope.launch {
            getFood(DataProvider.user!!.uid)

        }
    }

    private fun deleteFoodFromDatabase(food: FoodInfoModel) {
        viewModelScope.launch {
            val response = customFoodRepository.deleteFoodFromDatabase(
                DataProvider.user!!.uid,
                food,
            )
            when (response) {
                is Response.Failure -> {

                }

                Response.Loading -> {

                }

                is Response.Success -> {
                    _foodInfoState.value =
                        _foodInfoState.value.toMutableList().apply { remove(food) }
                }
            }
        }

    }

    private fun getFood(uid: String) {
        viewModelScope.launch {
            customFoodRepository.getFood(uid).collectLatest { resource ->
                when(resource){
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _foodInfoState.value = resource.data?.toList() ?: emptyList()
                        Log.d("Test",_foodInfoState.value.toString())
                    }
                }

            }
        }
    }

}