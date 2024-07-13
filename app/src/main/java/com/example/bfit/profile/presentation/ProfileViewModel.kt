package com.example.bfit.profile.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.profile.domain.repository.ProfileRepository
import com.example.bfit.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (
    private val profileRepository: ProfileRepository
) : ViewModel() {
    // List of cars
    private var _profile = mutableStateOf(UserInfo())
    val profile: State<UserInfo> = _profile

    init {
        // The function will be called when the viewModel gets called
        profileRepository.getProfileRealtime(DataProvider.user?.uid ?: "null")
            // onEach will trigger whenever a new value is retrieved
            .onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        /* TODO: Handle the error */
                    }

                    is Resource.Success -> {
                        _profile.value = resource.data!!
                    }

                    is Resource.Loading -> {}
                }
            }.launchIn(viewModelScope)
    }
}