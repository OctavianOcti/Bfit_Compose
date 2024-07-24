package com.example.bfit.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bfit.authentication.presentation.login.LoginScreen
import com.example.bfit.authentication.presentation.register.RegisterScreen
import com.example.bfit.main.MainScreen
import com.example.bfit.navdrawerfeatures.adjust_calories.presentation.AdjustMacrosScreen
import com.example.bfit.navdrawerfeatures.diary.presentation.DiaryScreen
import com.example.bfit.navdrawerfeatures.goals.presentation.GoalsScreen

@Composable
fun MyNavigationHost(
    navController: NavHostController = rememberNavController(),
    isUserLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if(isUserLoggedIn) Main else Login,
    ) {
        composable<Login> {
          LoginScreen({ navController.navigate(Register) }, { navController.navigate(Main) })
        }
        composable<Register> {
            RegisterScreen {
                navController.navigate(Login)

            }
        }
        composable<Main> {
            MainScreen (
                {navController.navigate(Login)}, {navController.navigate(Goals)} , {navController.navigate(AdjustMacros(it))}, {navController.navigate(Diary)}
            )
        }
        composable<Goals> {
            GoalsScreen(
                { navController.navigate(Main)}, {navController.navigate(AdjustMacros(it))}
            )
            }

        composable<AdjustMacros> {
            val userInfo: AdjustMacros = it.toRoute()
            AdjustMacrosScreen(userInfo.userInfo,
                { navController.navigate(Goals) },
                { navController.navigate(Main) }
            )
        }

        composable<Diary> {
            DiaryScreen{
                navController.navigate(Main)
            }
        }

    }

}