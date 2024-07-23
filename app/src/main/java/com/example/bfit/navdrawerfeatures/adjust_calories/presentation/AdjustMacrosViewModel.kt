package com.example.bfit.navdrawerfeatures.adjust_calories.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.MacrosUseCases
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository.MacrosRepository
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdjustMacrosViewModel @Inject constructor(
    private val macrosRepository: MacrosRepository,
    private val macrosUseCases: MacrosUseCases
) : ViewModel() {

//    private var _profile = mutableStateOf(UserInfo())
//    val profile: State<UserInfo> = _profile

    private var _profile = mutableStateOf(UserInfo())
    val profile: State<UserInfo> = _profile

    var state by mutableStateOf(MacrosState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        Log.d("DataProvider.uid", DataProvider.user?.uid ?: "null")
        Log.d("AdjustMacrosViewModel", "init block")


        viewModelScope.launch {
            macrosRepository.getProfileRealtime(DataProvider.user?.uid ?: "null")
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Log.d("AdjustMacrosViewModel", "error")
                        }

                        is Resource.Success -> {
                            _profile.value = resource.data!!
                        }

                        is Resource.Loading -> {}
                    }
                }
        }
    }

    fun onEvent(event: MacrosEvent) {
        when (event) {
            is MacrosEvent.CalorieChanged -> {
                state = state.copy(calories = getClosestDivisibleValue(event.calorie))
                Log.d("State calorie", state.calories)

            }

            is MacrosEvent.CarbChanged -> {
                state = state.copy(carb = event.carb)
            }

            is MacrosEvent.FatChanged -> {
                state = state.copy(fat = event.fat)
            }

            is MacrosEvent.ProteinChanged -> {
                state = state.copy(protein = event.protein)
            }

            is MacrosEvent.ProteinPercentageChanged -> {
                state = state.copy(proteinPercentage = event.proteinPercentage)
            }

            is MacrosEvent.CarbPercentageChanged -> {
                state = state.copy(carbPercentage = event.carbPercentage)
            }

            is MacrosEvent.FatPercentageChanged -> {
                state = state.copy(fatPercentage = event.fatPercentage)
            }

            is MacrosEvent.ResetMacros -> {
                Log.d("Macros.ResetMacros","Am intrat")
                this.onEvent(MacrosEvent.NewUser(event.userInfoList))
                Log.d("AdjustMacrosViewModel calories",state.calories)
            }
            is MacrosEvent.SaveMacros -> {
                if(event.userExists){
                    updateProfileDocument(
                    )

                }
                else {
                    setProfileDocument(
                        event.gender,
                        event.activityLevel,
                        event.goal,
                        event.age,
                        event.weight,
                        event.height,
                        event.protein,
                        event.carb,
                        event.fat,
                        event.proteinPercentage,
                        event.carbPercentage,
                        event.fatPercentage,
                        event.calories)
                }
            }

            is MacrosEvent.NewUser -> {
                val newUser = UserInfo(
                    event.userInfoList[0],
                    event.userInfoList[1],
                    event.userInfoList[2],
                    event.userInfoList[3].toInt(),
                    event.userInfoList[4].toFloat(),
                    event.userInfoList[5].toFloat()
                )
                this.onEvent(MacrosEvent.CalorieChanged(newUser.getCalories().toString()))
                this.onEvent(MacrosEvent.ProteinChanged(newUser.getProtein().toString()))
                this.onEvent(MacrosEvent.CarbChanged(newUser.getCarb().toString()))
                this.onEvent(MacrosEvent.FatChanged(newUser.getFat().toString()))
                this.onEvent(MacrosEvent.ProteinPercentageChanged(newUser.getProteinPercentage().toString()))
                this.onEvent(MacrosEvent.CarbPercentageChanged(newUser.getCarbPercentage().toString()))
                this.onEvent(MacrosEvent.FatPercentageChanged(newUser.getFatPercentage().toString()))
            }

            is MacrosEvent.ExistingUser -> {
                this.onEvent(MacrosEvent.ProteinChanged(event.userInfo.getProtein().toString()))
                this.onEvent(MacrosEvent.CarbChanged(event.userInfo.getCarb().toString()))
                this.onEvent(MacrosEvent.FatChanged(event.userInfo.getFat().toString()))
                this.onEvent(MacrosEvent.CalorieChanged(event.userInfo.getCalories().toString()))
                this.onEvent(MacrosEvent.ProteinPercentageChanged(event.userInfo.getProteinPercentage().toString()))
                this.onEvent(MacrosEvent.CarbPercentageChanged(event.userInfo.getCarbPercentage().toString()))
                this.onEvent(MacrosEvent.FatPercentageChanged(event.userInfo.getFatPercentage().toString()))
            }
        }
    }

    private fun submitData() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
    private fun setProfileDocument(
        gender: String,
        activityLevel: String,
        goal: String,
        age: Int,
         weight: Float,
         height: Float,
         protein: Int,
         carb: Int,
         fat: Int,
         proteinPercentage: Float,
         carbPercentage: Float,
         fatPercentage: Float,
         calories: Int
        ) = viewModelScope.launch  {

        val response = macrosRepository.setProfileDocument(
            DataProvider.user!!.uid,
            gender,
            activityLevel,
            goal,
            age,
            weight,
            height,
            protein,
            carb,
            fat,
            proteinPercentage,
            carbPercentage,
            fatPercentage,
            calories
        )
        when (response) {
            is Response.Failure -> Log.d("AdjustMacrosViewModel","Failure")
            Response.Loading -> {}
            is Response.Success -> submitData()
        }
    }

    private fun updateProfileDocument(

    ) = viewModelScope.launch{
        val response = macrosRepository.updateProfileDocument(
            DataProvider.user!!.uid,
            state.calories.toInt(),
            state.protein.toInt(),
            state.carb.toInt(),
            state.fat.toInt(),
            state.proteinPercentage.toFloat(),
            state.carbPercentage.toFloat(),
            state.fatPercentage.toFloat()
        )
        when (response) {
            is Response.Failure -> Log.d("AdjustMacrosViewModel","Failure")
            Response.Loading -> {}
            is Response.Success -> submitData()
        }

    }

    private fun getClosestDivisibleValue(calorieAmount: String): String {
        return macrosUseCases.getClosestDivisibleValue.execute(calorieAmount.toInt(), 36).toString()
    }

    fun validateCalorieAmount(calorieAmount: String): Boolean {
        return macrosUseCases.validateCalorieAmount.execute(calorieAmount)
    }
    fun validateMacrosPercentages(): Boolean{
        return macrosUseCases.validateMacrosPercentages.execute(
            proteinPercentage = state.proteinPercentage.toFloat(),
            carbPercentage = state.carbPercentage.toFloat(),
            fatPercentage = state.fatPercentage.toFloat()
            )
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}