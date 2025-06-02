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
import com.example.bfit.navdrawerfeatures.addFood.presentation.AddFoodScreen
import com.example.bfit.navdrawerfeatures.adjust_calories.presentation.AdjustMacrosScreen
import com.example.bfit.navdrawerfeatures.apiFoodInfo.presentation.ApiFoodInfoScreen
import com.example.bfit.navdrawerfeatures.customMealFood.common.presentation.CustomMealFood
import com.example.bfit.navdrawerfeatures.customMealFood.createFood.presentation.CreateFoodScreen
import com.example.bfit.navdrawerfeatures.diary.presentation.DiaryScreen
import com.example.bfit.navdrawerfeatures.foodInfo.presentation.FoodInfoScreen
import com.example.bfit.navdrawerfeatures.goals.presentation.GoalsScreen
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddScreen
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.navdrawerfeatures.showMealsFood.presentation.ShowMealFoodScreen
import kotlin.reflect.typeOf

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
              navigateToLogin = {navController.navigate(Login)},
                navigateToGoals =  {navController.navigate(Goals)} ,
                navigateToMacros = {navController.navigate(AdjustMacros(it))},
               navigateDoDiary =  {navController.navigate(Diary)},
                navigateToCustomMealFood = {navController.navigate(CustomMealFood)}
            )
        }
        composable<Goals> {
            GoalsScreen(
                { navController.navigate(Main)},
                {navController.navigate(AdjustMacros(it))}
            )
        }

        composable<CustomMealFood>{
            CustomMealFood(
                navigateToMain = {navController.navigate(Main)},
                navigateToCreateFood = {navController.navigate(CreateFood)}
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
                {navController.navigate(AddFood(it))},
                //{navController.navigate(ShowMealsFood(it,it))}
                {meal,formattedDate -> navController.navigate(ShowMealsFood(meal,formattedDate))}
            )
        }

        composable<AddFood>{
            val addFoodInfo: AddFood = it.toRoute()
            AddFoodScreen(
                formattedDate = addFoodInfo.formattedDate,
                {navController.navigate(Diary)},
                {date->navController.navigate(QuickAdd(date))},
                {foodInfoModel, meal, formattedDate ->navController.navigate(ApiFoodInfo(foodInfoModel,meal,formattedDate))  }
            )
        }
        composable<QuickAdd>{
            val quickAddInfo:QuickAdd= it.toRoute()
            QuickAddScreen(
                formattedDate = quickAddInfo.formattedDate
            ) { date -> navController.navigate(AddFood(date)) }
        }
        composable<ShowMealsFood> {
            val mealRouteData: ShowMealsFood = it.toRoute()
            ShowMealFoodScreen(
                meal = mealRouteData.meal,
                formattedDate =  mealRouteData.formattedDate,
                navigateToDiary = {navController.navigate(Diary)},
               // navigateToDiary = {navController.navigateUp()},
                {foodInfoModel, meal, formattedDate ->navController.navigate(FoodInfo(foodInfoModel,meal,formattedDate))  }
                // navigateToFoodInfo = {navController.navigate(FoodInfo(it))}
            )
        }
        composable<CreateFood>{
            CreateFoodScreen(navigateToCustomMealFood = {navController.navigate(CustomMealFood)})
        }

        composable<FoodInfo> (
            //typeMap = mapOf(typeOf<FoodInfoModel>() to FoodInfoType)
            typeMap = mapOf(typeOf<FoodInfoModel>() to parcelableType<FoodInfoModel>())
        ){
            val foodInfo: FoodInfo = it.toRoute()
            FoodInfoScreen(
                foodInfoModel = foodInfo.foodInfo,
                meal = foodInfo.meal,
                formattedDate = foodInfo.formattedDate,
                navigateToShowMealsFood = {navController.navigate(ShowMealsFood(foodInfo.meal,foodInfo.formattedDate))}
            )

        }
        composable<ApiFoodInfo> (
            typeMap = mapOf(typeOf<FoodInfoModel>() to parcelableType<FoodInfoModel>())
        ){
            val apiFoodInfo: ApiFoodInfo = it.toRoute()
            ApiFoodInfoScreen(
                foodInfoModel = apiFoodInfo.foodInfo,
                meal = apiFoodInfo.meal,
                formattedDate = apiFoodInfo.formattedDate,
                navigateToAddFood = {navController.navigate(AddFood(it))}
            )
        }
    }

}