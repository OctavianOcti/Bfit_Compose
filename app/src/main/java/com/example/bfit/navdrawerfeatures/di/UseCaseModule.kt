package com.example.bfit.navdrawerfeatures.di

import com.example.bfit.navdrawerfeatures.addFood.domain.repository.AddFoodRepository
import com.example.bfit.navdrawerfeatures.addFood.domain.use_case.AddFoodUseCases
import com.example.bfit.navdrawerfeatures.addFood.domain.use_case.GetFoodByBarcodeUseCase
import com.example.bfit.navdrawerfeatures.addFood.domain.use_case.GetFoodUseCase
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.MacrosUseCases
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.GetClosestDivisibleValue
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.ValidateCalorieAmount
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.ValidateMacrosPercentages
import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.ApiFoodInfoUseCases
import com.example.bfit.navdrawerfeatures.foodInfo.domain.data.AndroidServingPatternValidator
import com.example.bfit.navdrawerfeatures.foodInfo.domain.FoodInfoUseCases
import com.example.bfit.navdrawerfeatures.foodInfo.domain.ValidateServing
import com.example.bfit.navdrawerfeatures.goals.domain.GoalsUseCases
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateAge
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateHeight
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateInputData
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateWeight
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidHeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidWeightPatternValidator
import com.example.bfit.navdrawerfeatures.quickAdd.domain.QuickAddUseCases
import com.example.bfit.navdrawerfeatures.quickAdd.domain.ValidateData
import com.example.bfit.navdrawerfeatures.quickAdd.domain.data.AndroidQuantityPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGoalsUseCases(): GoalsUseCases {
        return GoalsUseCases(
            validateAge = ValidateAge(),
            validateWeight = ValidateWeight(AndroidWeightPatternValidator()),
            validateHeight = ValidateHeight(AndroidHeightPatternValidator()),
            validateInputData = ValidateInputData()
        )
    }

    @Provides
    fun provideAdjustMacrosUseCases(): MacrosUseCases {
        return MacrosUseCases(
            validateCalorieAmount = ValidateCalorieAmount(),
            validateMacrosPercentages = ValidateMacrosPercentages(),
            getClosestDivisibleValue = GetClosestDivisibleValue()
        )
    }
    @Provides
    fun provideQuickAddUseCases(): QuickAddUseCases{
        return QuickAddUseCases(
            validateData = ValidateData(AndroidQuantityPatternValidator()),
            validateInputData = com.example.bfit.navdrawerfeatures.quickAdd.domain.ValidateInputData()
        )
    }
    @Provides
    fun provideFoodInfoUseCases(): FoodInfoUseCases{
        return FoodInfoUseCases(
            validateServing = ValidateServing(AndroidServingPatternValidator())
        )
    }
    @Provides
    fun provideAddFoodUseCases(addFoodRepository: AddFoodRepository): AddFoodUseCases{
        return AddFoodUseCases(
            getFoodUseCase = GetFoodUseCase(addFoodRepository),
            getFoodByBarcodeUseCase = GetFoodByBarcodeUseCase(addFoodRepository)
        )
    }
    @Provides
    fun provideApiFoodInfoUseCases():ApiFoodInfoUseCases{
        return ApiFoodInfoUseCases(
            validateServing = com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.ValidateServing(com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.data.AndroidServingPatternValidator()),
            validateInputData = com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.ValidateInputData()
        )
    }
}
