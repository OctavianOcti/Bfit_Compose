package com.example.bfit.navdrawerfeatures.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars

@Composable
fun HistoryChart(data: List<Bars>) {
    ColumnChart(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp),
        data = data,  // pass the dynamic data here
        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = 3.dp,
            thickness = 20.dp
        ),
    )
}

