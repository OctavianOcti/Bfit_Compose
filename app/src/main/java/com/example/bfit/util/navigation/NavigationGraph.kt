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
import com.example.bfit.navdrawerfeatures.addFood.AddFoodScreen
import com.example.bfit.navdrawerfeatures.adjust_calories.presentation.AdjustMacrosScreen
import com.example.bfit.navdrawerfeatures.diary.presentation.DiaryScreen
import com.example.bfit.navdrawerfeatures.goals.presentation.GoalsScreen
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddScreen
import com.example.bfit.navdrawerfeatures.showMealsFood.ShowMealFoodScreen

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
          LoginScreen({ navController.navigate(Register) },
              { navController.navigate(Main) })
        }
        composable<Register> {
            RegisterScreen {
                navController.navigate(Login)

            }
        }
        composable<Main> {
            MainScreen (
                {navController.navigate(Login)},
                {navController.navigate(Goals)} ,
                {navController.navigate(AdjustMacros(it))},
                {navController.navigate(Diary)}
            )
        }
        composable<Goals> {
            GoalsScreen(
                { navController.navigate(Main)},
                {navController.navigate(AdjustMacros(it))}
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
            DiaryScreen(
                {navController.navigate(Main)},
                {navController.navigate(AddFood)},
                //{navController.navigate(ShowMealsFood(it,it))}
                {meal,formattedDate -> navController.navigate(ShowMealsFood(meal,formattedDate))}
            )
        }

        composable<AddFood>{
            AddFoodScreen(
                {navController.navigate(Diary)},
                {navController.navigate(QuickAdd)}
            )
        }
        composable<QuickAdd>{
            QuickAddScreen{
                navController.navigate(AddFood)
            }
        }
        composable<ShowMealsFood> {
            val meal : ShowMealsFood = it.toRoute()
            ShowMealFoodScreen(
                meal.meal,
                meal.formattedDate,
            ) { navController.navigate(Diary) }


        }

    }

}