package com.example.bfit.main.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.bfit.main.domain.repository.AuthStateRepository
import com.example.bfit.util.FirebaseSignInResponse
import com.example.bfit.util.Response
import com.example.bfit.util.SignOutResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AuthStateRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthStateRepository {
    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        // 1.
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            // 4.
            trySend(auth.currentUser)
            Log.i(TAG, "User: ${auth.currentUser?.uid ?: "Not authenticated"}")
        }
        // 2.
        auth.addAuthStateListener(authStateListener)
        // 3.
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser)

    override suspend fun signOut(): SignOutResponse {
        return try {
            auth.signOut()
            Response.Success(true)
        }
        catch (e: java.lang.Exception) {
            Response.Failure(e)
        }
    }


}