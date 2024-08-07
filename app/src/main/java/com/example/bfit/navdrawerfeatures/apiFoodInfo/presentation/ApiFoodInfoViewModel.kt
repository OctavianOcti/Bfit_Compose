package com.example.bfit.navdrawerfeatures.apiFoodInfo.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.ApiFoodInfoUseCases
import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.repository.ApiFoodInfoRepository
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddEvent
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddViewModel.ValidationEvent
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ApiFoodInfoViewModel @Inject constructor(
    private val apiFoodInfoUseCases: ApiFoodInfoUseCases,
    private val apiFoodInfoRepository: ApiFoodInfoRepository

) : ViewModel() {
    var state by mutableStateOf(ApiFoodInfoState())
        private set
    var  _isFoodDuplicated = mutableStateOf(false)
    val isFoodDuplicated: State<Boolean> = _isFoodDuplicated

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
    private val dayInfoMap: MutableMap<String, Any> = mutableMapOf()
    private val foodMap: MutableMap<String, Any> = mutableMapOf()

    fun onEvent(event: ApiFoodInfoEvent) {
        when (event) {
            is ApiFoodInfoEvent.CarbsChanged -> {
                state = state.copy(carbs = event.carbs)
            }

            is ApiFoodInfoEvent.FatChanged -> {
                state = state.copy(fat = event.fat)
            }

            is ApiFoodInfoEvent.KcalChanged -> {
                state = state.copy(kcal = event.kcal)
            }

            is ApiFoodInfoEvent.MealChanged -> {
                state = state.copy(meal = event.meal)
            }

            is ApiFoodInfoEvent.ProteinChanged -> {
                state = state.copy(protein = event.protein)
            }

            is ApiFoodInfoEvent.ServingSizeChanged -> {
                state = state.copy(servingSize = event.servingSize)
            }

            is ApiFoodInfoEvent.FoodBrandChanged -> {
                state = state.copy(foodBrand = event.foodBrand)
            }

            is ApiFoodInfoEvent.FoodNameChanged -> {
                state = state.copy(foodName = event.foodName)
            }

            is ApiFoodInfoEvent.PreviousServingSizeChanged -> {
                state = state.copy(previousServingSize = event.previousServingSize)
            }

            is ApiFoodInfoEvent.PreviousFoodCarbChanged -> {
                state = state.copy(previousFoodCarb = event.previousFoodCarb)
            }

            is ApiFoodInfoEvent.PreviousFoodFatChanged -> {
                state = state.copy(previousFoodFat = event.previousFoodFat)
            }

            is ApiFoodInfoEvent.PreviousFoodKcalChanged -> {
                state = state.copy(previousFoodKcal = event.previousFoodKcal)
            }

            is ApiFoodInfoEvent.PreviousFoodProteinChanged -> {
                state = state.copy(previousFoodProtein = event.previousFoodProtein)
            }

            is ApiFoodInfoEvent.MacrosChanged -> {
                state = state.copy(servingSize = event.servingSize)
                updateMacrosValues()
            }

            ApiFoodInfoEvent.SubmitData -> {
                getPreviousTotalMacros()
            }

            is ApiFoodInfoEvent.FormattedDateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
            }

            is ApiFoodInfoEvent.PreviousTotalCarbChanged -> {
                state=state.copy(previousTotalCarb = event.previousTotalCarbChanged)
            }
            is ApiFoodInfoEvent.PreviousTotalFatChanged -> {
                state=state.copy(previousTotalFat = event.previousTotalFatChanged)
            }
            is ApiFoodInfoEvent.PreviousTotalKcalChanged -> {
                state=state.copy(previousTotalKcal = event.previousTotalKcal)
            }
            is ApiFoodInfoEvent.PreviousTotalProteinChanged -> {
                state=state.copy(previousTotalProtein = event.previousTotalProtein)
            }
        }
    }
    private fun getPreviousTotalMacros(){
        viewModelScope.launch {
            updateMacrosValues()
            updateMaps()
            apiFoodInfoRepository.getPreviousTotalMacros(
                DataProvider.user!!.uid,
                state.formattedDate
            ).onEach { result ->
                when (result){
                    is Resource.Error -> {
                        Log.d("getPreviousTotalMacros","failure")
                        setNewDocument()
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Log.d("getPreviousTotalMacros","success")
                        onEvent(ApiFoodInfoEvent.PreviousTotalKcalChanged(result.data?.previousTotalKcal.toString()))
                        onEvent(ApiFoodInfoEvent.PreviousTotalFatChanged(result.data?.previousTotalFat.toString()))
                        onEvent(ApiFoodInfoEvent.PreviousTotalCarbChanged(result.data?.previousTotalCarb.toString()))
                        onEvent(ApiFoodInfoEvent.PreviousTotalProteinChanged(result.data?.previousTotalProtein.toString()))
                        getPreviousFoodMacros()
                    }
                }
            }.launchIn(this)

        }
    }

    private fun getPreviousFoodMacros(){
        viewModelScope.launch {
            updateMaps()
           apiFoodInfoRepository.getPreviousFoodMacros(
                DataProvider.user!!.uid,
                state.formattedDate,
                state.meal,
                state.foodName
            ).onEach { result ->
                when (result){
                    is Resource.Error -> {
                        Log.d("GetPreviousFoodMacros error",result.message.toString())
                        updateFoodDocument()
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        // _isFoodDuplicate.value = !_isFoodDuplicate.value
                        _isFoodDuplicated.value= true
                        Log.d("getpreviousFoodMacros","Success")


                    }
                }
            }.launchIn(this)

        }
    }
    private suspend fun updateFoodDocument(){
        val response = apiFoodInfoRepository.updateFoodDocument(
            DataProvider.user!!.uid,
            state.formattedDate,
            dayInfoMap,
            foodMap,
            state.kcal,
            state.protein,
            state.carbs,
            state.fat,
            state.previousTotalKcal,
            state.previousTotalProtein,
            state.previousTotalCarb,
            state.previousTotalFat,
            state.meal,
            state.foodName
        )
        when(response){
            is Response.Failure -> {
                Log.d("updateFoodDocument","Failure")
            }
            Response.Loading -> {}
            is Response.Success -> {
                Log.d("updateFoodDocument","Success")
                validationEventChannel.send(ValidationEvent.Succes)}
        }
    }
    private suspend fun setNewDocument() {
        val response = apiFoodInfoRepository.setNewDocument(
            DataProvider.user!!.uid,
            state.formattedDate,
            dayInfoMap,
            foodMap,
            state.meal,
            state.foodName
        )
        when (response) {
            is Response.Failure -> {
                Log.d("setNewDocument", "Failure")
            }

            Response.Loading -> {}
            is Response.Success -> {
                Log.d("setNewDocument", "Success")
                validationEventChannel.send(ValidationEvent.Succes)
            }
        }
    }



//    private fun updateMaps() {
//        dayInfoMap["total_kcal"] = state.kcal
//        dayInfoMap["total_protein"] = state.protein
//        dayInfoMap["total_carb"] = state.carbs
//        dayInfoMap["total_fat"] = state.fat
//
//        foodMap["kcal"] = round(state.kcal.toDouble())
//        foodMap["protein"] = round(state.protein.toDouble())
//        foodMap["carb"] = round(state.carbs.toDouble())
//        foodMap["fat"] = round(state.fat.toDouble())
//        foodMap["serving_size"] = round(state.servingSize.toDouble())
//        if (state.foodBrand.isNotEmpty()) foodMap["brand"] = state.foodBrand
//    }

    @SuppressLint("SimpleDateFormat")
    fun convertToFirebaseTimestamp(dateString: String): Timestamp? {
        return try {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val date: Date = dateFormat.parse(dateString) ?: return null
            Timestamp(date)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
private fun updateMaps() {
    val timestamp = convertToFirebaseTimestamp(state.formattedDate)
    timestamp?.let { dayInfoMap["timestamp"] = timestamp }
    dayInfoMap["total_kcal"] = state.kcal.toDouble()
    dayInfoMap["total_protein"] = state.protein.toDouble()
    dayInfoMap["total_carb"] = state.carbs.toDouble()
    dayInfoMap["total_fat"] = state.fat.toDouble()

    foodMap["kcal"] = round(state.kcal.toDouble())
    foodMap["protein"] = round(state.protein.toDouble())
    foodMap["carb"] = round(state.carbs.toDouble())
    foodMap["fat"] = round(state.fat.toDouble())
    foodMap["serving_size"] = round(state.servingSize.toDouble())
    foodMap["servingType"]= "food"
}

    private fun updateMacrosValues() {
        this.onEvent(ApiFoodInfoEvent.ServingSizeChanged((round(state.servingSize.toDouble()).toString())))
        this.onEvent(ApiFoodInfoEvent.KcalChanged(round(state.servingSize.toDouble() * state.kcal.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(ApiFoodInfoEvent.ProteinChanged(round(state.servingSize.toDouble() * state.protein.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(ApiFoodInfoEvent.CarbsChanged(round(state.servingSize.toDouble() * state.carbs.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(ApiFoodInfoEvent.FatChanged(round(state.servingSize.toDouble() * state.fat.toDouble() / state.previousServingSize.toDouble()).toString()))
        this.onEvent(ApiFoodInfoEvent.PreviousServingSizeChanged(state.servingSize))
    }
    fun validateInputData(
        context: Context,
        meal: String,

    ): Boolean {
        return apiFoodInfoUseCases.validateInputData.execute(
            context = context,
            meal = meal,
        )
    }

    fun validateServing(serving: String): Boolean {
        return apiFoodInfoUseCases.validateServing.execute(serving)
    }

    sealed class ValidationEvent {
        object Succes : ValidationEvent()
    }
}