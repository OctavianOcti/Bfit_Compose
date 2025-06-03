package com.example.bfit.navdrawerfeatures.di


import com.example.bfit.navdrawerfeatures.addFood.data.repository.AddFoodRepositoryImpl
import com.example.bfit.navdrawerfeatures.addFood.domain.repository.AddFoodRepository
import com.example.bfit.navdrawerfeatures.adjust_calories.data.repository.MacrosRepositoryImpl
import com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository.MacrosRepository
import com.example.bfit.navdrawerfeatures.apiFoodInfo.data.repository.ApiFoodInfoRepositoryImpl
import com.example.bfit.navdrawerfeatures.apiFoodInfo.domain.repository.ApiFoodInfoRepository
import com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.data.CreateFoodRepositoryImpl
import com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.domain.repository.CreateFoodRepository
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.data.CustomFoodRepositoryImpl
import com.example.bfit.navdrawerfeatures.customMealFood.food.viewFood.domain.CustomFoodRepository
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.data.CustomMealRepositoryImpl
import com.example.bfit.navdrawerfeatures.customMealFood.meal.viewMeals.domain.CustomMealRepository
import com.example.bfit.navdrawerfeatures.diary.data.repository.DiaryRepositoryImpl
import com.example.bfit.navdrawerfeatures.diary.domain.repository.DiaryRepository
import com.example.bfit.navdrawerfeatures.foodInfo.data.repository.FoodInfoRepositoryImpl
import com.example.bfit.navdrawerfeatures.foodInfo.domain.repository.FoodInfoRepository
import com.example.bfit.navdrawerfeatures.goals.data.repository.GoalsRepositoryImpl

import com.example.bfit.navdrawerfeatures.goals.domain.repository.GoalsRepository
import com.example.bfit.navdrawerfeatures.home.data.HomeRepositoryImpl
import com.example.bfit.navdrawerfeatures.home.domain.HomeRepository
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

    @Binds
    abstract fun bindApiFoodInfoRepository(apiFoodInfoRepositoryImpl: ApiFoodInfoRepositoryImpl): ApiFoodInfoRepository

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl) : HomeRepository

    @Binds
    abstract fun bindCustomFoodRepository(foodRepositoryImpl: CustomFoodRepositoryImpl): CustomFoodRepository

    @Binds
    abstract fun bindCreateFoodRepository(createFoodRepositoryImpl: CreateFoodRepositoryImpl): CreateFoodRepository

    @Binds
    abstract fun bindCustomMealRepository(customMealRepositoryImpl: CustomMealRepositoryImpl): CustomMealRepository
}