package com.example.bfit.navdrawerfeatures.di


import com.example.bfit.navdrawerfeatures.addFood.data.repository.AddFoodRepositoryImpl
import com.example.bfit.navdrawerfeatures.addFood.domain.repository.AddFoodRepository
import com.example.bfit.navdrawerfeatures.adjust_calories.data.repository.MacrosRepositoryImpl
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository.MacrosRepository
import com.example.bfit.navdrawerfeatures.diary.data.repository.DiaryRepositoryImpl
import com.example.bfit.navdrawerfeatures.diary.domain.repository.DiaryRepository
import com.example.bfit.navdrawerfeatures.foodInfo.data.repository.FoodInfoRepositoryImpl
import com.example.bfit.navdrawerfeatures.foodInfo.domain.repository.FoodInfoRepository
import com.example.bfit.navdrawerfeatures.goals.data.repository.GoalsRepositoryImpl

import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
import com.example.bfit.navdrawerfeatures.quickAdd.data.QuickAddRepositoryImpl
import com.example.bfit.navdrawerfeatures.quickAdd.domain.repository.QuickAddRepository
import com.example.bfit.navdrawerfeatures.profile.data.repository.ProfileRepositoryImpl
import com.example.bfit.navdrawerfeatures.profile.domain.repository.ProfileRepository
import com.example.bfit.navdrawerfeatures.showMealsFood.data.repository.ShowMealsFoodImpl
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.repository.ShowMealsFoodRepository
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

    @Binds
    abstract fun bindQuickAddRepository(quickAddRepositoryImpl: QuickAddRepositoryImpl): QuickAddRepository

    @Binds
    abstract fun bindFoodInfoRepository(foodInfoRepositoryImpl: FoodInfoRepositoryImpl): FoodInfoRepository

    @Binds
    abstract fun bindShowMealsFoodInfoRepository(showMealsFoodImpl: ShowMealsFoodImpl) : ShowMealsFoodRepository

    @Binds
    abstract fun bindAddFoodRepository(addFoodRepositoryImpl: AddFoodRepositoryImpl): AddFoodRepository
}