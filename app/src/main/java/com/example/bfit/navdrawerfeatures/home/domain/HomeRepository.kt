package com.example.bfit.navdrawerfeatures.home.domain

import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getProfileRealtime(userUid: String): Flow<Resource<UserInfo>>
    fun getTotalDailyInfo(userUid: String,formattedDate:String): Flow<Resource<DailyInfo>>

}