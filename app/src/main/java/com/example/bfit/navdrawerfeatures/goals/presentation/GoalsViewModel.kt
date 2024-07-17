package com.example.bfit.navdrawerfeatures.goals.presentation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.goals.domain.GoalsUseCases
import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
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
class GoalsViewModel @Inject constructor(
    private val goalsRepository: GoalsRepository,
    private val goalsUseCases: GoalsUseCases
) : ViewModel() {
    // List of cars
//    private var _profile = mutableStateOf(UserInfo())
//    val profile: State<UserInfo> = _profile

    var state by mutableStateOf(GoalsState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        // The function will be called when the viewModel gets called
        goalsRepository.getProfileRealtime(DataProvider.user?.uid ?: "null")
            // onEach will trigger whenever a new value is retrieved
            .onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        /* TODO: Handle the error */
                    }

                    is Resource.Success -> {
                        //_profile.value = resource.data!!
                        state = state.copy(gender = resource.data!!.getGender())
                        state = state.copy(age = resource.data.getAge().toString())
                        state = state.copy(weight = resource.data.getWeight().toString())
                        state = state.copy(height = resource.data.getHeight().toString())
                        state = state.copy(goal = resource.data.getGoal())
                        state = state.copy(activityLevel = resource.data.getActivityLevel())
                    }

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
    }

    fun setProfileDocument() = viewModelScope.launch  {
        Log.d("GoalsViewModel","SetProfileDocumen")
        Log.d("GoalsViewModel","Uid = ${ DataProvider.user!!.uid} , gender = ${state.gender}, weight = ${state.weight.toFloat()}, height = ${state.height.toFloat()}, age = ${state.age.toInt()}, activityLevel = ${state.activityLevel}, goal = ${state.goal} " )
       val response = goalsRepository.setProfileDocument1(
            DataProvider.user!!.uid,
            state.gender,
            state.weight.toFloat(),
            state.height.toFloat(),
            state.age.toInt(),
            state.activityLevel,
            state.goal
        )
        when (response) {
            is Response.Failure -> Log.d("GoalsViewModel","Failure")
            Response.Loading -> {}
            is Response.Success -> submitData()
        }
            }



    fun onEvent(event: GoalsEvent) {
        when (event) {
            is GoalsEvent.ActivityLevelChanged -> {
                state = state.copy(activityLevel = event.activityLevel)
            }

            is GoalsEvent.AgeChanged -> {
                state = state.copy(age = event.age)
            }

            is GoalsEvent.GenderChanged -> {
                state = state.copy(gender = event.gender)
            }

            is GoalsEvent.GoalChanged -> {
                state = state.copy(goal = event.goal)
            }

            is GoalsEvent.HeightChanged -> {
                state = state.copy(height = event.height)
            }

            is GoalsEvent.WeightChanged -> {
                state = state.copy(weight = event.weight)
            }
        }

    }

    private fun submitData() {
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun validateInputData(
        context: Context,
        gender: String,
        weight: String,
        height: String,
        age: String,
        activityLevel: String,
        goal: String
    ): Boolean {
        return goalsUseCases.validateInputData.execute(
            context,
            gender,
            weight,
            height,
            age,
            activityLevel,
            goal
        )
    }

    fun validateAge(age: String): Boolean {
        return goalsUseCases.validateAge.execute(age)
    }

    fun validateHeight(height: String): Boolean {
        return goalsUseCases.validateHeight.execute(height)
    }

    fun validateWeight(weight: String): Boolean {
        return goalsUseCases.validateWeight.execute(weight)
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}