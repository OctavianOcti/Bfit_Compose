package com.example.bfit.navdrawerfeatures.goals.domain.repository

import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.util.Resource
import com.example.bfit.util.Response
import kotlinx.coroutines.flow.Flow

interface GoalsRepository {
    fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>>
    fun setProfileDocument(userUid : String,
        gender: String,
        weight: Float,
        height: Float,
        age: Int,
        activityLevel: String,
        goal: String
    ): Flow<Resource<Any>>

    suspend fun setProfileDocument1(userUid : String,
                           gender: String,
                           weight: Float,
                           height: Float,
                           age: Int,
                           activityLevel: String,
                           goal: String
    ): Response<Boolean>


}