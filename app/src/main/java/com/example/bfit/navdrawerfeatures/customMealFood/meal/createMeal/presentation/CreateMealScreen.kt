package com.example.bfit.navdrawerfeatures.customMealFood.meal.createMeal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.common.presentation.AlertDialogWarning
import com.example.bfit.navdrawerfeatures.common.presentation.LogoSection
import com.example.bfit.navdrawerfeatures.common.presentation.TextInputDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMealScreen(
) {
    val viewModel: CreateMealViewModel = hiltViewModel()
    val state= viewModel.state
    val context = LocalContext.current
    val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextKcal = remember { mutableStateOf("") }
    val inputTextCarbs = remember { mutableStateOf("") }
    val inputTextProtein = remember { mutableStateOf("") }
    val inputTextFat = remember { mutableStateOf("") }
    val inputTextFoodName= remember { mutableStateOf("") }

    val warningDialogState = remember { mutableStateOf(false) }
    val warningDialogTitle = remember { mutableStateOf("") }
    val warningDialogShowCancelButton = remember { mutableStateOf(true) }
    val warningDialogAlertMessage = remember { mutableStateOf("") }

    val showInputDialog: (String) -> Unit = { title ->
        inputDialogTitle.value = title
        inputDialogState.value = true
    }
    val showWarningDialog: (String, Boolean, String?) -> Unit = { title, showCancel, message ->
        warningDialogTitle.value = title
        warningDialogShowCancelButton.value = showCancel
        warningDialogAlertMessage.value = message ?: ""
        warningDialogState.value = true
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { }) { //navigateToScreen()
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "NavigateCustomMeals"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.ic_bfit_logo_background),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.baseline_save_24),
                            tint = Color(0xFF4CAF50),
                            contentDescription = "Save meal"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* your action here */ },
                containerColor = colorResource(id = R.color.gradientLightYellow2),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // or any other icon
                    contentDescription = "Add something",
                    tint = Color.Yellow
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(id = R.color.darkGrey))
                    .padding(paddingValues)
            ) {
                Text(
                    text = stringResource(id = R.string.create_a_meal),
                    color = colorResource(id = R.color.orange),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

               LayoutWithFields(
                    state = state,
                    onMealNameClick = {showInputDialog("Enter meal name")},

                    )
                LogoSection()
            }
        }
    )
    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = {  },
        visible = warningDialogState.value,
        showCancelButton = warningDialogShowCancelButton.value,
        alertMessage = warningDialogAlertMessage.value
    )
    TextInputDialog(
        title = inputDialogTitle.value,
        text = when (inputDialogTitle.value) {
            "Enter calorie amount" -> inputTextKcal.value
            "Enter carbohydrate amount" -> inputTextCarbs.value
            "Enter protein amount" -> inputTextProtein.value
            "Enter fat amount" -> inputTextFat.value
            "Enter food name" -> inputTextFoodName.value
            else -> ""
        },
        onTextChange = {
            when (inputDialogTitle.value) {
                "Enter calorie amount" -> inputTextKcal.value = it
                "Enter carbohydrate amount" -> inputTextCarbs.value = it
                "Enter protein amount" -> inputTextProtein.value = it
                "Enter fat amount" -> inputTextFat.value = it
                "Enter food name" -> inputTextFoodName.value = it
            }
        },
        onDismissRequest = {
            inputDialogState.value = false
            inputTextKcal.value = ""
            inputTextCarbs.value = ""
            inputTextProtein.value= ""
            inputTextFat.value =""
            inputTextFoodName.value=""
        },
        onConfirm = {
            when (inputDialogTitle.value) {
                "Enter calorie amount" -> viewModel.onEvent(
                    CreateMealEvent.KcalChanged(
                        inputTextKcal.value
                    )
                )
                "Enter carbohydrate amount" ->viewModel.onEvent(
                    CreateMealEvent.CarbsChanged(
                        inputTextCarbs.value
                    )
                )
                "Enter protein amount" ->viewModel.onEvent(
                    CreateMealEvent.ProteinChanged(
                        inputTextProtein.value
                    )
                )
                "Enter fat amount" ->viewModel.onEvent(CreateMealEvent.FatChanged(inputTextFat.value))
                "Enter food name" ->viewModel.onEvent(
                    CreateMealEvent.MealNameChanged(
                        inputTextFoodName.value
                    )
                )
            }
            inputDialogState.value = false
        },
        visible = inputDialogState.value,
        validate = when (inputDialogTitle.value) {
            "Enter calorie amount" -> { _: String -> true }
            "Enter carbohydrate amount" -> { _: String -> true }
            "Enter protein amount" -> { _: String -> true }
            "Enter fat amount" -> { _: String -> true }
            "Enter food name" -> {_: String-> true}
            else -> { _: String -> true }
        },
        validationMessage = when (inputDialogTitle.value) {
            "Enter calorie amount",
            "Enter carbohydrate amount",
            "Enter protein amount",
            "Enter fat amount",
            "Enter food name" ,
            "Enter serving size amount" -> "The quantity should be a maximum one decimal number"
            else -> ""
        },
        keyboardType =  when (inputDialogTitle.value) {
            "Enter food name" -> KeyboardType.Unspecified
            else -> KeyboardType.Number
        }
    )

}