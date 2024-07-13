package com.example.bfit.main.domain.repository

import com.example.bfit.util.AuthStateResponse
import com.example.bfit.util.FirebaseSignInResponse
import com.example.bfit.util.SignOutResponse
import kotlinx.coroutines.CoroutineScope

interface AuthStateRepository {

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun signOut(): SignOutResponse
}