package com.example.bfit.navdrawerfeatures.diary.domain.repository

import com.example.bfit.util.Resource
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    fun getMealTexts(uid: String, formattedDate: String): Flow<Resource<List<String>>>
}