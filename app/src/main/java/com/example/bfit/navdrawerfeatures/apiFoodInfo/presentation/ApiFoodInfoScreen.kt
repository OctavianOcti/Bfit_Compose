package com.example.bfit.navdrawerfeatures.apiFoodInfo.presentation

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddEvent
import com.example.bfit.navdrawerfeatures.quickAdd.presentation.QuickAddViewModel
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiFoodInfoScreen(
    foodInfoModel: FoodInfoModel,
    meal:String,
    formattedDate:String,
   // navigateToShowMealsFood: ()-> Unit ={}
    navigateToAddFood: (String) -> Unit = {},
) {
    val viewModel: ApiFoodInfoViewModel = hiltViewModel()
    val state = viewModel.state
    val isFoodDuplicate = viewModel.isFoodDuplicated
    val context = LocalContext.current

    val dialogState = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogOptions = remember { mutableStateOf(listOf<String>()) }
    val selectedOption = remember { mutableStateOf("") }


    val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextServingSize = remember { mutableStateOf("") }

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
    val showWarningDialog: (String, Boolean, String?) -> Unit = { title, showCancel, message ->
        warningDialogTitle.value = title
        warningDialogShowCancelButton.value = showCancel
        warningDialogAlertMessage.value = message ?: ""
        warningDialogState.value = true
    }

    val servingValidator: (String) -> Boolean = { serving -> viewModel.validateServing(serving) }

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
        viewModel.onEvent(ApiFoodInfoEvent.FormattedDateChanged(formattedDate))
    }
    LaunchedEffect(key1 = state.formattedDate) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                ApiFoodInfoViewModel.ValidationEvent.Succes -> {
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

    LaunchedEffect(key1 = foodInfoModel) {
        Log.d("foodInfoScreen", foodInfoModel.toString())
        if(!foodInfoModel.isEmpty()){
            viewModel.onEvent(ApiFoodInfoEvent.MealChanged(meal))
            viewModel.onEvent(ApiFoodInfoEvent.ServingSizeChanged(foodInfoModel.servingSize))
            Log.d("ApifoodScreen serving size", foodInfoModel.servingSize)
            viewModel.onEvent(ApiFoodInfoEvent.KcalChanged(foodInfoModel.enercKcal))
            viewModel.onEvent(ApiFoodInfoEvent.CarbsChanged(foodInfoModel.carb))
            viewModel.onEvent(ApiFoodInfoEvent.ProteinChanged(foodInfoModel.prot))
            viewModel.onEvent(ApiFoodInfoEvent.FatChanged(foodInfoModel.fat))
            viewModel.onEvent(ApiFoodInfoEvent.FoodNameChanged(foodInfoModel.label))
            viewModel.onEvent(ApiFoodInfoEvent.FoodBrandChanged(foodInfoModel.brand))
            viewModel.onEvent(ApiFoodInfoEvent.FormattedDateChanged(formattedDate))

            viewModel.onEvent(ApiFoodInfoEvent.PreviousFoodKcalChanged(foodInfoModel.enercKcal))
            viewModel.onEvent(ApiFoodInfoEvent.PreviousFoodProteinChanged(foodInfoModel.prot))
            viewModel.onEvent(ApiFoodInfoEvent.PreviousFoodCarbChanged(foodInfoModel.carb))
            viewModel.onEvent(ApiFoodInfoEvent.PreviousFoodFatChanged(foodInfoModel.fat))
            viewModel.onEvent(ApiFoodInfoEvent.PreviousServingSizeChanged(foodInfoModel.servingSize))
        }

    }

//    LaunchedEffect(key1 = context) {
//        viewModel.validationEvents.collect { event ->
//            when (event) {
//               // ApiFoodInfoViewModel.ValidationEvent.Succes -> navigateToAddFood(state.formattedDate)
//                ApiFoodInfoViewModel.ValidationEvent.Succes -> {}
//            }
//        }
//    }





    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navigateToAddFood(state.formattedDate)}) { //navigateToScreen()
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
                        if(!viewModel.validateInputData(context,state.meal)){
                            showWarningDialog("Please fill in all the required information!",
                                false,
                                "Please ensure all fields are filled out correctly before submitting.")
                        } else viewModel.onEvent(ApiFoodInfoEvent.SubmitData)

                    }) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.baseline_save_24),
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
                    text = state.foodName,
                    color = colorResource(id = R.color.blueForDarkGrey),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.foodBrand,
                    color = colorResource(id = R.color.blueForDarkGrey),
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LayoutWithFields(
                    state = state,
                    onMealClick = {showDialog("Choose your meal", mealOptions)},
                    onServingSizeClick = { showInputDialog("Enter serving size amount") }
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
                "Choose your meal" -> viewModel.onEvent(ApiFoodInfoEvent.MealChanged(selectedOption.value))
            }
            dialogState.value = false
        },
        visible = dialogState.value
    )

    TextInputDialog(
        title = inputDialogTitle.value,
        text = when (inputDialogTitle.value) {
            "Enter serving size amount" -> inputTextServingSize.value
            else -> ""
        },
        onTextChange = {
            when (inputDialogTitle.value) {
                "Enter serving size amount" -> inputTextServingSize.value = it
            }
        },
        onDismissRequest = {
            inputDialogState.value = false
             inputTextServingSize.value= ""

        },
        onConfirm = {
            when (inputDialogTitle.value) {
                // "Enter serving size amount" -> viewModel.onEvent(FoodInfoEvent.ServingSizeChanged(inputTextServingSize.value))
                "Enter serving size amount" -> viewModel.onEvent(ApiFoodInfoEvent.MacrosChanged(inputTextServingSize.value))
            }
             inputTextServingSize.value= ""
            inputDialogState.value = false
        },
        visible = inputDialogState.value,
        validate = when (inputDialogTitle.value) {
            "Enter serving size amount" -> servingValidator
            else -> { _: String -> true }
        },
        validationMessage = when (inputDialogTitle.value) {
            "Enter serving size amount" -> "Enter a valid number (maximum one decimal allowed)"
            else -> ""
        },
        keyboardType = KeyboardType.Number
    )

    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = { viewModel.onEvent(ApiFoodInfoEvent.SubmitData) },
        //onConfirm = { Log.d("GoalsScreen","onConfirm")},
        visible = warningDialogState.value,
        showCancelButton = warningDialogShowCancelButton.value,
        alertMessage = warningDialogAlertMessage.value
    )
}

@Composable
fun LayoutWithFields(
    state: ApiFoodInfoState,
    onMealClick: () -> Unit,
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
                value = state.meal.ifEmpty { "" },
                onFieldClick = onMealClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.serving_size_g),
                value = state.servingSize.ifEmpty { "" },
                onFieldClick = onServingSizeClick,
                textColor = colorResource(id = R.color.blueForDarkGrey)
            )
            Divider()
            FieldRow(
                label = "Kcal",
                value = state.kcal.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = "Protein",
                value = state.protein.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = "Carb",
                value = state.carbs.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = "Fat",
                value = state.fat.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
        }
    }
}