package com.example.bfit.navdrawerfeatures.di

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
import dagger.hilt.components.SingletonComponent

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
}
