package com.example.bfit.navdrawerfeatures.quickAdd.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.bfit.navdrawerfeatures.common.presentation.Divider
import com.example.bfit.navdrawerfeatures.common.presentation.FieldRow
import com.example.bfit.navdrawerfeatures.common.presentation.LogoSection
import com.example.bfit.navdrawerfeatures.common.presentation.TextInputDialog
import com.example.bfit.navdrawerfeatures.common.presentation.getStringArrayFromResource
import com.example.bfit.navdrawerfeatures.goals.presentation.SingleChoiceDialogSample

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickAddScreen(
    formattedDate:String,
    navigateToAddFood : (String) -> Unit = {}
){
    val viewModel:QuickAddViewModel = hiltViewModel()
    val state= viewModel.state
    val isFoodDuplicate = viewModel.isFoodDuplicated
    val context = LocalContext.current

    val dialogState = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogOptions = remember { mutableStateOf(listOf<String>()) }
    val selectedOption = remember { mutableStateOf("") }

    val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextKcal = remember { mutableStateOf("") }
    val inputTextCarbs = remember { mutableStateOf("") }
    val inputTextProtein = remember { mutableStateOf("") }
    val inputTextFat = remember { mutableStateOf("") }
    val inputTextFoodName= remember { mutableStateOf("") }
    val inputTextServingSize= remember { mutableStateOf("") }

    val warningDialogState = remember { mutableStateOf(false) }
    val warningDialogTitle = remember { mutableStateOf("") }
    val warningDialogShowCancelButton = remember { mutableStateOf(true) }
    val warningDialogAlertMessage = remember { mutableStateOf("") }

    val mealOptions = getStringArrayFromResource(id = R.array.select_meal)

    val showDialog: (String, List<String>) -> Unit = { title, options ->
        dialogTitle.value = title
        dialogOptions.value = options
        dialogState.value = true
    }

    val showInputDialog: (String) -> Unit = { title ->
        inputDialogTitle.value = title
        inputDialogState.value = true
    }

    val dataValidator: (String) -> Boolean = { data -> viewModel.validateData(data) }
    val showWarningDialog: (String, Boolean, String?) -> Unit = { title, showCancel, message ->
        warningDialogTitle.value = title
        warningDialogShowCancelButton.value = showCancel
        warningDialogAlertMessage.value = message ?: ""
        warningDialogState.value = true
    }
    LaunchedEffect(key1 = isFoodDuplicate.value) {
        Log.d("AddScreen isfoodduplicate",isFoodDuplicate.value.toString())
        if(isFoodDuplicate.value) {
            Log.d("addscreen","Da")
            showWarningDialog("Another food with ${state.foodName} name exists",
                true,
                "Would you like to override the information for this food?")
        }
        else Log.d("addscrenn","Nu")
    }

    LaunchedEffect(key1=true) {
        Log.d("formattedDate1",formattedDate)
        viewModel.onEvent(QuickAddEvent.FormattedDateChanged(formattedDate))
    }

    LaunchedEffect(key1 = state.formattedDate) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    QuickAddViewModel.ValidationEvent.Success -> {
                        Log.d("Launcheffect date", state.formattedDate)
                        Toast.makeText(
                            context,
                            "Your food has been tracked!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToAddFood(formattedDate)
                    }
                }
            }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = { navigateToAddFood(state.formattedDate) }) { //navigateToScreen()
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
                        if(!viewModel.validateInputData(context,state.meal,state.kcal,state.protein,state.carbs,state.fat,state.foodName,state.servingSize)){
                            showWarningDialog("Please fill in all the required information!",
                                false,
                                "Please ensure all fields are filled out correctly before submitting.")
                        } else viewModel.onEvent(QuickAddEvent.SubmitData)
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
                    onMealClick = {showDialog("Choose your meal", mealOptions)},
                    onProteinClick = {showInputDialog("Enter protein amount")},
                    onCarbClick = {showInputDialog("Enter carbohydrate amount")},
                    onFatClick = {showInputDialog("Enter fat amount")},
                    onKcalClick = {showInputDialog("Enter calorie amount")},
                    onServingSizeClick = {showInputDialog("Enter serving size amount")},
                    onFoodNameClick = {showInputDialog("Enter food name")},
                )
                LogoSection()
            }
        }
    )
    SingleChoiceDialogSample(
        title = dialogTitle.value,
        options = dialogOptions.value,
        selectedOption = selectedOption.value,
        onOptionSelected = { selectedOption.value = it },
        onDismissRequest = { dialogState.value = false },
        onConfirm = {
            when (dialogTitle.value) {
                "Choose your meal" -> viewModel.onEvent(QuickAddEvent.MealChanged(selectedOption.value))
            }
            dialogState.value = false
        },
        visible = dialogState.value
    )
    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = { viewModel.onEvent(QuickAddEvent.SubmitData) },
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
            "Enter serving size amount" -> inputTextServingSize.value
            else -> ""
        },
        onTextChange = {
            when (inputDialogTitle.value) {
                "Enter calorie amount" -> inputTextKcal.value = it
                "Enter carbohydrate amount" -> inputTextCarbs.value = it
                "Enter protein amount" -> inputTextProtein.value = it
                "Enter fat amount" -> inputTextFat.value = it
                "Enter food name" -> inputTextFoodName.value = it
                "Enter serving size amount" -> inputTextServingSize.value = it
            }
        },
        onDismissRequest = {
            inputDialogState.value = false
            inputTextKcal.value = ""
            inputTextCarbs.value = ""
            inputTextProtein.value= ""
            inputTextFat.value =""
            inputTextFoodName.value=""
            inputTextServingSize.value=""
        },
        onConfirm = {
            when (inputDialogTitle.value) {
                "Enter calorie amount" -> viewModel.onEvent(QuickAddEvent.KcalChanged(inputTextKcal.value))
                "Enter carbohydrate amount" ->viewModel.onEvent(QuickAddEvent.CarbsChanged(inputTextCarbs.value))
                "Enter protein amount" ->viewModel.onEvent(QuickAddEvent.ProteinChanged(inputTextProtein.value))
                "Enter fat amount" ->viewModel.onEvent(QuickAddEvent.FatChanged(inputTextFat.value))
                "Enter food name" ->viewModel.onEvent(QuickAddEvent.FoodNameChanged(inputTextFoodName.value))
                "Enter serving size amount" ->viewModel.onEvent(QuickAddEvent.ServingSizeChanged(inputTextServingSize.value))
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
            "Enter serving size amount" -> dataValidator
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

@Composable
fun LayoutWithFields(
    state : QuickAddState,
    onMealClick: () -> Unit,
    onKcalClick: () -> Unit,
    onCarbClick: () -> Unit,
    onProteinClick: () -> Unit,
    onFatClick: () -> Unit,
    onFoodNameClick: () -> Unit,
    onServingSizeClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 25.dp),
        shape = RoundedCornerShape(16.dp),
        color = colorResource(id = R.color.ic_bfit_logo_background)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            FieldRow(
                label = stringResource(id = R.string.meal),
                value = state.meal.ifEmpty { stringResource(id = R.string.select_meal) },
                onFieldClick = onMealClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.kcal),
                value = state.kcal.ifEmpty { stringResource(id = R.string.enter_calories_amount) },
                onFieldClick = onKcalClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.protein_g),
                value = state.protein.ifEmpty { stringResource(id = R.string.enter_protein_amount) },
                onFieldClick = onProteinClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.carbohydrates),
                value = state.carbs.ifEmpty { stringResource(id = R.string.enter_carbs_amount) },
                onFieldClick = onCarbClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.fat_g),
                value = state.fat.ifEmpty { stringResource(id = R.string.enter_fat_amount) },
                onFieldClick = onFatClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)

            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.food_name),
                value = state.foodName.ifEmpty { stringResource(id = R.string.enter_food_name) },
                onFieldClick = onFoodNameClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.serving_size_g),
                value = state.servingSize.ifEmpty { stringResource(id = R.string.enter_serving_size) },
                onFieldClick = onServingSizeClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
        }
    }
}
