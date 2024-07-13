package com.example.bfit.main.presentation.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem (
    val title : String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)