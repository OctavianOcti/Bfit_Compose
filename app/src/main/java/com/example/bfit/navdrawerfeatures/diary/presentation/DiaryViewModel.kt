package com.example.bfit.navdrawerfeatures.diary.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.navdrawerfeatures.diary.domain.repository.DiaryRepository
import com.example.bfit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {


    //    var _state by mutableStateOf(DiaryState())
//        private set
//    val state: State<DiaryState> = _state
    var state by mutableStateOf(DiaryState())
        private set


    fun onEvent(event: DiaryEvents) {
        when (event) {
            is DiaryEvents.DateChanged -> {
                state = state.copy(formattedDate = event.formattedDate)
                viewModelScope.launch {
                    diaryRepository.getMealTexts(DataProvider.user!!.uid, state.formattedDate)
                        .onEach { result ->
                            when (result) {
                                is Resource.Error -> {}
                                is Resource.Loading -> {}
                                is Resource.Success -> {
                                    state = state.copy(
                                        mealTexts = result.data ?: emptyList(),
                                        formattedDate = event.formattedDate
                                    )
                                    Log.d("DiaryViewModel", state.mealTexts[0] + "    " + state.mealTexts[1])
                                }
                            }
                        }.launchIn(this)
                }
            }
        }
    }

}