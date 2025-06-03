package com.example.bfit.navdrawerfeatures.customMealFood.food.createFood.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFoodScreen(
    navigateToCustomMealFood : () -> Unit = {}
)
{

    val viewModel: CreateFoodViewModel = hiltViewModel()
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
    LaunchedEffect(Unit) {
        viewModel.validationEvents.collect { event ->
            when (event) {
               CreateFoodViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Your food has been tracked!",
                        Toast.LENGTH_SHORT
                    ).show()
                   navigateToCustomMealFood()
                }
            }
        }
    }


    val dataValidator: (String) -> Boolean = { data -> viewModel.validateData(data) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { navigateToCustomMealFood() }) { //navigateToScreen()
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "NavigateToMain"
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
                        if(!viewModel.validateInputData(context,state.foodName,state.kcal,state.protein,state.carbs,state.fat,state.foodName)){
                            showWarningDialog("Please fill in all the required information!",
                                false,
                                "Please ensure all fields are filled out correctly before submitting.")
                        } else viewModel.onEvent(CreateFoodEvent.SubmitData)
                        //viewModel.onEvent(QuickAddEvent.SubmitData)
                    }) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.ic_baseline_check_24),
                            tint = Color(0xFF4CAF50),
                            contentDescription = "Edit notes"
                        )
                    }
                },
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(colorResource(id = R.color.darkGrey))
                    .padding(paddingValues)
            ) {
                Text(
                    text = stringResource(id = R.string.quick_add),
                    color = colorResource(id = R.color.orange),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LayoutWithFields(
                    state = state,
                    onFoodNameClick = {showInputDialog("Enter food name")},
                    onProteinClick = {showInputDialog("Enter protein amount")},
                    onCarbClick = {showInputDialog("Enter carbohydrate amount")},
                    onFatClick = {showInputDialog("Enter fat amount")},
                    onKcalClick = {showInputDialog("Enter calorie amount")},

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
                    CreateFoodEvent.KcalChanged(
                        inputTextKcal.value
                    )
                )
                "Enter carbohydrate amount" ->viewModel.onEvent(
                    CreateFoodEvent.CarbsChanged(
                        inputTextCarbs.value
                    )
                )
                "Enter protein amount" ->viewModel.onEvent(
                    CreateFoodEvent.ProteinChanged(
                        inputTextProtein.value
                    )
                )
                "Enter fat amount" ->viewModel.onEvent(CreateFoodEvent.FatChanged(inputTextFat.value))
                "Enter food name" ->viewModel.onEvent(
                    CreateFoodEvent.FoodNameChanged(
                        inputTextFoodName.value
                    )
                )
            }
            inputDialogState.value = false
        },
        visible = inputDialogState.value,
        validate = when (inputDialogTitle.value) {
            "Enter calorie amount" -> dataValidator
            "Enter carbohydrate amount" -> dataValidator
            "Enter protein amount" -> dataValidator
            "Enter fat amount" -> dataValidator
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