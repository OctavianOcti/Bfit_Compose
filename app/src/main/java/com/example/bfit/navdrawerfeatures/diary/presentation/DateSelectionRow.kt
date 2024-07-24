package com.example.bfit.navdrawerfeatures.diary.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bfit.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun DateSelectionRow(
    state: DiaryState,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    var selectedDate by remember { mutableStateOf(state.formattedDate) }

    fun updateDate(newDate: Date) {
        val formattedDate = dateFormat.format(newDate)
        selectedDate = formattedDate
        onDateSelected(formattedDate)
    }

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                updateDate(selectedCalendar.time)
            }, year, month, day
        )
        datePickerDialog.show()
    }

    // Initialize selectedDate with the state if it's empty
    if (selectedDate.isEmpty()) {
        selectedDate = dateFormat.format(Date())
    }

    fun handlePreviousDate() {
        val calendar = Calendar.getInstance().apply {
            time = dateFormat.parse(selectedDate) ?: Date()
            add(Calendar.DAY_OF_YEAR, -1)
        }
        updateDate(calendar.time)
    }

    fun handleNextDate() {
        val calendar = Calendar.getInstance().apply {
            time = dateFormat.parse(selectedDate) ?: Date()
            add(Calendar.DAY_OF_YEAR, 1)
        }
        updateDate(calendar.time)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { handlePreviousDate() },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.darkGrey)),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.PreviousDateButtonText),
                color = Color(0xFFFF5722),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }

        TextButton(
            onClick = { showDatePicker() },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = selectedDate,
                color = colorResource(id = R.color.darkWhite),
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { handleNextDate() },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.darkGrey)),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.NextDayButtonText),
                color = Color(0xFFFF5722),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}