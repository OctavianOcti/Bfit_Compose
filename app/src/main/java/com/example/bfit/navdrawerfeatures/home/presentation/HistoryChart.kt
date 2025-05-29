package com.example.bfit.navdrawerfeatures.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars

@Composable
fun HistoryChart(){
    ColumnChart(
        modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp),
        data = remember {
            listOf(
                Bars(
                    label = "Jan",
                    values = listOf(
                        Bars.Data(label = "Linux", value = 50.0, color = Brush.verticalGradient(listOf(
                            Color.DarkGray, Color.Black))),
                        Bars.Data(label = "Windows", value = 70.0, color = SolidColor(Color.Red))
                    ),
                ),
                Bars(
                    label = "Feb",
                    values = listOf(
                        Bars.Data(label = "Linux", value = 80.0, color = Brush.verticalGradient(listOf(
                            Color.DarkGray, Color.Black))),
                        Bars.Data(label = "Windows", value = 60.0, color = SolidColor(Color.Red))
                    ),
                )
            )
        },
        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = 3.dp,
            thickness = 20.dp
        ),

        )
}

