package com.example.bfit.navdrawerfeatures.diary.presentation

sealed class DiaryEvents {
    data class DateChanged(val formattedDate:String): DiaryEvents()
}