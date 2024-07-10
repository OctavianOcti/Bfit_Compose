package com.example.bfit.authentication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bfit.authentication.presentation.login.LoginScreen
import com.example.bfit.authentication.presentation.register.RegisterScreen

@Composable
fun MyNavigationHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController, startDestination = Login ){
        composable<Login> {
            LoginScreen {
                navController.navigate(Register)
            }
        }
        composable<Register> {
        RegisterScreen{
            navController.navigate(Login)
        }
        }
    }

}