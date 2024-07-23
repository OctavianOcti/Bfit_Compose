package com.example.bfit.navdrawerfeatures.di

import com.example.bfit.navdrawerfeatures.adjust_calories.domain.MacrosUseCases
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.GetClosestDivisibleValue
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.ValidateCalorieAmount
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.ValidateMacrosPercentages
import com.example.bfit.navdrawerfeatures.goals.domain.GoalsUseCases
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateAge
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateHeight
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateInputData
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateWeight
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidHeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidWeightPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
}
