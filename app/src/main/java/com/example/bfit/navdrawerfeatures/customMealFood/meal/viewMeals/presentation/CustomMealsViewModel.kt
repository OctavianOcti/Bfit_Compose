package com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.CustomMealRepository
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.MealInfoModel
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomMealsViewModel @Inject constructor(
    private var customMealsRepository: CustomMealRepository

): ViewModel(){
    private val _mealInfoState = MutableStateFlow<List<MealInfoModel>>(emptyList())
    val mealInfoState: StateFlow<List<MealInfoModel>> = _mealInfoState
    fun onEvent(event: ViewMealsEvent) {
        when (event) {
            is ViewMealsEvent.DeleteMeals -> deleteMealFromDatabase(event.meal)
        }
    }
    init{
        viewModelScope.launch {
            getMeals(DataProvider.user!!.uid)

        }
    }

    private fun deleteMealFromDatabase(meal: MealInfoModel) {
        viewModelScope.launch {
            val response = customMealsRepository.deleteMealFromDatabase(
                DataProvider.user!!.uid,
                meal,
            )
            when (response) {
                is Response.Failure -> {
                }

                Response.Loading -> {

                }

                is Response.Success -> {
                    _mealInfoState.value =
                        _mealInfoState.value.toMutableList().apply { remove(meal) }

                }
            }
        }

    }

    private fun getMeals(uid: String) {
        viewModelScope.launch {
            customMealsRepository.getMeals(uid).collectLatest { resource ->
                when(resource){
                    is Resource.Error -> {
                        Log.d("MealInfoState",resource.toString())
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _mealInfoState.value = resource.data?.toList() ?: emptyList()
                        Log.d("MealInfoState",_mealInfoState.value.toString())
                    }
                }

            }
        }
    }
}