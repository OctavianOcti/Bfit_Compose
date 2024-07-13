package com.example.bfit.profile.domain.repository

import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfileOnce(): Flow<Resource<List<UserInfo>>>
    fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>>
}