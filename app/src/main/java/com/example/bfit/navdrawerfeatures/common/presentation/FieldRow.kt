package com.example.bfit.navdrawerfeatures.common.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R

@Composable
fun FieldRow(label: String, value: String, onFieldClick: () -> Unit,textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, end = 15.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = colorResource(id = R.color.darkWhite),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = onFieldClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = value,
              //  color = colorResource(id = R.color.blueForDarkGrey),
                color= textColor,
                textAlign = TextAlign.End
            )
        }
    }
}