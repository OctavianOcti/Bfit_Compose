package com.example.bfit.navdrawerfeatures.foodInfo.presentation

import android.util.Log
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.common.presentation.Divider
import com.example.bfit.navdrawerfeatures.common.presentation.FieldRow
import com.example.bfit.navdrawerfeatures.common.presentation.LogoSection
import com.example.bfit.navdrawerfeatures.common.presentation.TextInputDialog
import com.example.bfit.navdrawerfeatures.goals.presentation.GoalsEvent
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.example.bfit.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInfoScreen(
    foodInfoModel: FoodInfoModel,
    meal:String,
    formattedDate:String,
    navigateToShowMealsFood: ()-> Unit ={}
) {
    val viewModel: FoodInfoViewModel = hiltViewModel()
    val state = viewModel.state
    LaunchedEffect(key1 = foodInfoModel) {
        Log.d("foodInfoScreen", foodInfoModel.toString())
        if(!foodInfoModel.isEmpty()){
            viewModel.onEvent(FoodInfoEvent.MealChanged(meal))
            viewModel.onEvent(FoodInfoEvent.ServingSizeChanged(foodInfoModel.servingSize))
            viewModel.onEvent(FoodInfoEvent.KcalChanged(foodInfoModel.enercKcal))
            viewModel.onEvent(FoodInfoEvent.CarbsChanged(foodInfoModel.carb))
            viewModel.onEvent(FoodInfoEvent.ProteinChanged(foodInfoModel.prot))
            viewModel.onEvent(FoodInfoEvent.FatChanged(foodInfoModel.fat))
            viewModel.onEvent(FoodInfoEvent.FoodNameChanged(foodInfoModel.label))
            viewModel.onEvent(FoodInfoEvent.FoodBrandChanged(foodInfoModel.brand))

        }

    }

    val context = LocalContext.current
    val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextServingSize = remember { mutableStateOf("") }

    val showInputDialog: (String) -> Unit = { title ->
        inputDialogTitle.value = title
        inputDialogState.value = true
    }

    val servingValidator: (String) -> Boolean = { serving -> viewModel.validateServing(serving) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navigateToShowMealsFood()}) { //navigateToScreen()
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
//                        if(!viewModel.validateInputData(context,state.meal,state.kcal,state.protein,state.carbs,state.fat,state.foodName,state.servingSize)){
//                            showWarningDialog("Please fill in all the required information!",
//                                false,
//                                "Please ensure all fields are filled out correctly before submitting.")
//                        }
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
                    onServingSizeClick = { showInputDialog("Enter serving size amount") },
                )
                LogoSection()
            }
        }
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
           // inputTextServingSize.value= ""

        },
        onConfirm = {
            when (inputDialogTitle.value) {
                "Enter serving size amount" -> viewModel.onEvent(FoodInfoEvent.ServingSizeChanged(inputTextServingSize.value))

            }
           // inputTextServingSize.value= ""
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
        }
    )
}

@Composable
fun LayoutWithFields(
    state: FoodInfoState,

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
                value = state.meal.ifEmpty { "asd" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
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
                label = stringResource(id = R.string.kcal),
                value = state.kcal.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.protein_g),
                value = state.protein.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.carbohydrates),
                value = state.carbs.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.fat_g),
                value = state.fat.ifEmpty { "" },
                onFieldClick = {},
                textColor = colorResource(id = R.color.orange)
            )
        }
    }
}
