package com.example.bfit.navdrawerfeatures.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getStringArrayFromResource(id: Int): List<String> {
    val context = LocalContext.current
    return context.resources.getStringArray(id).toList()
}