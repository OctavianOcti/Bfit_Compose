package com.example.bfit.navdrawerfeatures.home.presentation
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.home.domain.HomeRepository
import com.example.bfit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel(){

    private var _profile = mutableStateOf(UserInfo())
    val profile: State<UserInfo> = _profile

    var state by mutableStateOf(HomeState())
        private set

    init{
        viewModelScope.launch {
              launch {
                homeRepository.getProfileRealtime(DataProvider.user?.uid ?: "null")
                .collectLatest { resource ->
                    when (resource) {
                        is Resource.Error -> {
                            Log.d("HomeViewModel", "error")
                        }

                        is Resource.Success -> {
                            _profile.value = resource.data!!
                        }

                        is Resource.Loading -> {}
                    }
                }
            }
        }
    }


    fun onEvent(event:HomeEvents){
        when(event){
            is HomeEvents.DateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
                    viewModelScope.launch {
                        launch {
                            homeRepository.getTotalDailyInfo(
                                userUid = DataProvider.user?.uid ?: "null",
                                formattedDate = state.formattedDate
                            )
                                .collectLatest { resource ->
                                    when (resource) {
                                        is Resource.Error -> {
                                            Log.d("HomeViewModel", "error")
                                            state = state.copy(dailyCalories = 0.0)
                                            state = state.copy(dailyCarb = 0.0)
                                            state = state.copy(dailyProtein = 0.0)
                                            state = state.copy(dailyFat = 0.0)
                                            state=state.copy(caloriesProgress = 0.0f)
                                            state=state.copy(proteinProgress = 0.0f)
                                            state=state.copy(carbsProgress = 0.0f)
                                            state=state.copy(fatProgress = 0.0f)
                                            state=state.copy(kcalLeft = profile.value.getCalories().toDouble())
                                            Log.d("Calories progress", state.caloriesProgress.toString())
                                        }
                                        is Resource.Success -> {
                                            state = state.copy(dailyCalories = resource.data!!.total_kcal)
                                            state = state.copy(dailyCarb = resource.data.total_carb)
                                            state = state.copy(dailyProtein = resource.data.total_protein)
                                            state = state.copy(dailyFat = resource.data.total_fat)
                                            state=state.copy(caloriesProgress = calculateCaloriesProgress())
                                            state=state.copy(proteinProgress = calculateMacroProgress(profile.value.getProtein(),state.dailyProtein.toFloat()))
                                            state=state.copy(carbsProgress = calculateMacroProgress(profile.value.getCarb(),state.dailyCarb.toFloat()))
                                            state=state.copy(fatProgress = calculateMacroProgress(profile.value.getFat(),state.dailyFat.toFloat()))
                                            state=state.copy(kcalLeft = profile.value.getCalories()- state.dailyCalories)
                                            Log.d("Calories progress", state.caloriesProgress.toString())
                                            Log.d("HomeViewModel + ${state.dailyCalories}", "succes")

                                        }
                                        is Resource.Loading -> {}
                                    }
                                }
                        }
                    }
            }
        }
    }

    private fun calculateCaloriesProgress():Float {
        return (state.dailyCalories.toFloat() / profile.value.getCalories().toFloat()).coerceIn(0f, 1f)
    }
    private fun calculateMacroProgress(macroProfile:Int,macroDaily:Float):Float {
        return (macroDaily/macroProfile.toFloat()).coerceIn(0f,1f)
    }
}