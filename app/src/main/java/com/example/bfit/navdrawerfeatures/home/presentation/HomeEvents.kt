package com.example.bfit.navdrawerfeatures.home.presentation

sealed class HomeEvents {
    data class DateChanged(val formattedDate:String): HomeEvents()
}