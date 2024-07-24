package com.example.bfit.navdrawerfeatures.di


import com.example.bfit.navdrawerfeatures.adjust_calories.data.repository.MacrosRepositoryImpl
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository.MacrosRepository
import com.example.bfit.navdrawerfeatures.diary.data.repository.DiaryRepositoryImpl
import com.example.bfit.navdrawerfeatures.diary.domain.repository.DiaryRepository
import com.example.bfit.navdrawerfeatures.goals.data.repository.GoalsRepositoryImpl

import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
import com.example.bfit.navdrawerfeatures.profile.data.repository.ProfileRepositoryImpl
import com.example.bfit.navdrawerfeatures.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class  RepositoryModule {

    @Binds
    abstract fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindGoalsRepository(goalsRepositoryImpl: GoalsRepositoryImpl): GoalsRepository

    @Binds
    abstract fun bindMacrosRepository(macrosRepositoryImpl: MacrosRepositoryImpl): MacrosRepository

    @Binds
    abstract fun bindDiaryRepository(diaryRepositoryImpl: DiaryRepositoryImpl): DiaryRepository
}