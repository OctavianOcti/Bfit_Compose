package com.example.bfit.navdrawerfeatures.quickAdd.presentation

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
import com.example.bfit.navdrawerfeatures.common.presentation.domain.round
import com.example.bfit.navdrawerfeatures.quickAdd.domain.QuickAddUseCases
import com.example.bfit.navdrawerfeatures.quickAdd.domain.repository.QuickAddRepository
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
class QuickAddViewModel @Inject constructor(
    //private val quickAddRepository: QuickAddRepository
    private val quickAddUseCases: QuickAddUseCases,
    private val quickAddRepository:QuickAddRepository
) : ViewModel() {
    var state by mutableStateOf(QuickAddState())
        private set

    var  _isFoodDuplicated = mutableStateOf(false)
    val isFoodDuplicated: State<Boolean> = _isFoodDuplicated

//    private val _isFoodDuplicate = MutableStateFlow(false)
//    val isFoodDuplicate: StateFlow<Boolean> get() = _isFoodDuplicate

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val dayInfoMap: MutableMap<String, Any> = mutableMapOf()
    private val foodMap: MutableMap<String, Any> = mutableMapOf()


    fun onEvent(event: QuickAddEvent) {
        when (event) {
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

            is QuickAddEvent.FormattedDateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
            }

            QuickAddEvent.SubmitData -> {
                getPreviousTotalMacros()
                //getPreviousFoodMacros()

            }

            is QuickAddEvent.PreviousTotalCarbChanged -> {
                state=state.copy(previousTotalCarb = event.previousTotalCarbChanged)
            }
            is QuickAddEvent.PreviousTotalFatChanged -> {
                state=state.copy(previousTotalFat = event.previousTotalFatChanged)
            }
            is QuickAddEvent.PreviousTotalKcalChanged -> {
                state=state.copy(previousTotalKcal = event.previousTotalKcal)
            }
            is QuickAddEvent.PreviousTotalProteinChanged -> {
                state=state.copy(previousTotalProtein = event.previousTotalProtein)
            }
        }
    }

    fun validateData(data: String): Boolean {
        return quickAddUseCases.validateData.execute(data)
    }


    private fun getPreviousTotalMacros(){
        viewModelScope.launch {
            updateMacrosValues()
            updateMaps()
            quickAddRepository.getPreviousTotalMacros(
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
                        onEvent(QuickAddEvent.PreviousTotalKcalChanged(result.data?.previousTotalKcal.toString()))
                        onEvent(QuickAddEvent.PreviousTotalFatChanged(result.data?.previousTotalFat.toString()))
                        onEvent(QuickAddEvent.PreviousTotalCarbChanged(result.data?.previousTotalCarb.toString()))
                        onEvent(QuickAddEvent.PreviousTotalProteinChanged(result.data?.previousTotalProtein.toString()))
                        getPreviousFoodMacros()
                    }
                }
            }.launchIn(this)

        }
    }
    private fun getPreviousFoodMacros(){
        viewModelScope.launch {
            updateMaps()
            quickAddRepository.getPreviousFoodMacros(
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
        val response = quickAddRepository.updateFoodDocument(
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
                validationEventChannel.send(ValidationEvent.Success)}
        }
    }

    private suspend fun setNewDocument() {
        val response = quickAddRepository.setNewDocument(
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
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }


    fun validateInputData(
        context: Context,
        meal: String,
        kcal: String,
        protein: String,
        carb: String,
        fat: String,
        foodName: String,
        servingSize: String
    ): Boolean {
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

    private fun updateMacrosValues() {
        this.onEvent(QuickAddEvent.KcalChanged(round(state.servingSize.toDouble() * state.kcal.toDouble() / 100).toString()))
        this.onEvent(QuickAddEvent.CarbsChanged(round(state.servingSize.toDouble() * state.carbs.toDouble() / 100).toString()))
        this.onEvent(QuickAddEvent.ProteinChanged(round(state.servingSize.toDouble() * state.protein.toDouble() / 100).toString()))
        this.onEvent(QuickAddEvent.FatChanged(round(state.servingSize.toDouble() * state.fat.toDouble() / 100).toString()))
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


    private fun submitData() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}