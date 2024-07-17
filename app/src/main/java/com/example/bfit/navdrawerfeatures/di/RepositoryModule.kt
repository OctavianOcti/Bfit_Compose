package com.example.bfit.navdrawerfeatures.di

import com.example.bfit.authentication.domain.repository.AuthRepository
import com.example.bfit.authentication.domain.use_case.AuthUseCases
import com.example.bfit.authentication.domain.use_case.ValidateEmail
import com.example.bfit.authentication.domain.use_case.ValidatePassword
import com.example.bfit.authentication.domain.use_case.data.AndroidEmailPatternValidator
import com.example.bfit.authentication.domain.use_case.data.AndroidPasswordPatternValidator
import com.example.bfit.authentication.domain.use_case.register.RegisterUser
import com.example.bfit.authentication.domain.use_case.register.ValidateRepeatedPassword
import com.example.bfit.navdrawerfeatures.goals.data.repository.GoalsRepositoryImpl
import com.example.bfit.navdrawerfeatures.goals.domain.GoalsUseCases
import com.example.bfit.navdrawerfeatures.goals.domain.HeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateAge
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateHeight
import com.example.bfit.navdrawerfeatures.goals.domain.ValidateWeight
import com.example.bfit.navdrawerfeatures.goals.domain.WeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidHeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.data.AndroidWeightPatternValidator
import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
import com.example.bfit.navdrawerfeatures.profile.data.repository.ProfileRepositoryImpl
import com.example.bfit.navdrawerfeatures.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindGoalsRepository(goalsRepositoryImpl: GoalsRepositoryImpl): GoalsRepository
}