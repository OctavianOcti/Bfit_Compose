package com.example.bfit.util.navigation

import kotlinx.serialization.Serializable

@Serializable
object Register

@Serializable
object Login

@Serializable
object Main

@Serializable
object Home

@Serializable
object Profile

@Serializable
object Goals

@Serializable
object Diary

@Serializable
data class AdjustMacros(val userInfo : List<String>)

