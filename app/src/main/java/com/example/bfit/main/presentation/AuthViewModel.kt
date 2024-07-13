package com.example.bfit.main.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bfit.main.domain.model.AuthState
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.repository.AuthStateRepository
import com.example.bfit.util.Response
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthStateRepository
): ViewModel() {
    // 3.
    val currentUser = getAuthState()
    var state by mutableStateOf(DataProvider.authState)

    init {
        // 2.
        getAuthState()
        Log.d("AuthViewModel","Am intrat")
    }

    //DataProvider.updateAuthState(currentUser)

    // 1.
    private fun getAuthState() = repository.getAuthState(viewModelScope)
    fun signOut() = CoroutineScope(Dispatchers.IO).launch {
        DataProvider.signOutResponse = Response.Loading
        DataProvider.signOutResponse = repository.signOut()
    }
}

//@HiltViewModel
//class AuthViewModel @Inject constructor(
//    private val repository: AuthStateRepository
//) : ViewModel() {
//    private val _authState = MutableStateFlow(AuthState.SignedOut)
//    val authState = _authState.asStateFlow()
//
//
//    init {
//        viewModelScope.launch {
//            repository.getAuthState(viewModelScope).collectLatest { user ->
//                DataProvider.updateAuthState(user)
//                _authState.value = DataProvider.authState
//            }
//        }
//    }
//
//    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
//        DataProvider.signOutResponse = Response.Loading
//        DataProvider.signOutResponse = repository.signOut()}
//}