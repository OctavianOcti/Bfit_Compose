package com.example.bfit


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.bfit.authentication.presentation.login.LoginScreen
import com.example.bfit.util.navigation.Login
import com.example.bfit.util.navigation.Main
import com.example.bfit.util.navigation.MyNavigationHost

import com.example.bfit.main.MainScreen
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val currentUser = authViewModel.currentUser.collectAsState().value
            DataProvider.updateAuthState(currentUser)
            if (DataProvider.isAuthenticated) {
                MyNavigationHost(isUserLoggedIn = true)
            } else {
               MyNavigationHost(isUserLoggedIn = false)
            }

        }
    }
}