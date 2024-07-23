package com.example.bfit.navdrawerfeatures.adjust_calories.domain.repository

import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow


interface MacrosRepository {
    fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>>
    suspend fun setProfileDocument(
        userUid: String,
        gender: String,
        activityLevel: String,
        goal: String,
        age: Int,
        weight: Float,
        height: Float,
        protein: Int,
        carb: Int,
        fat: Int,
        proteinPercentage: Float,
        carbPercentage: Float,
        fatPercentage: Float,
        calories: Int
    ): Response<Boolean>

    suspend fun updateProfileDocument(
        userUid: String,
        calories: Int,
        protein: Int,
        carb: Int,
        fat: Int,
        proteinPercentage: Float,
        carbPercentage: Float,
        fatPercentage: Float
    ): Response<Boolean>


}