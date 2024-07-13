package com.example.bfit.main.domain.model


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.bfit.util.FirebaseSignInResponse
import com.example.bfit.util.Response
import com.google.firebase.auth.FirebaseUser
import com.example.bfit.util.SignOutResponse

enum class AuthState {
    Authenticated, // Anonymously authenticated in Firebase.
    SignedIn, // Authenticated in Firebase using one of service providers, and not anonymous.
    SignedOut; // Not authenticated in Firebase.
}

object DataProvider {

    // 1.
    //var anonymousSignInResponse by mutableStateOf<FirebaseSignInResponse>(Response.Success(null))
    //var googleSignInResponse by mutableStateOf<FirebaseSignInResponse>(Response.Success(null))

    var signOutResponse by mutableStateOf<SignOutResponse>(Response.Success(false))


    var authState by mutableStateOf(AuthState.SignedOut)

    var user by mutableStateOf<FirebaseUser?>(null)


    var isAuthenticated by mutableStateOf(false)


    var isAnonymous by mutableStateOf(false)
      

    // 2.
    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user != null
        isAnonymous = user?.isAnonymous ?: false

        authState = when {
            isAuthenticated && isAnonymous -> AuthState.Authenticated
            isAuthenticated -> AuthState.SignedIn
            else -> AuthState.SignedOut
        }

    }
}