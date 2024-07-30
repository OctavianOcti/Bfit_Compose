package com.example.bfit.navdrawerfeatures.adjust_calories.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.bfit.navdrawerfeatures.common.presentation.Divider
import com.example.bfit.navdrawerfeatures.common.presentation.TextInputDialog



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdjustMacrosScreen(
    userInfoList: List<String>,
    navigateToGoals: () -> Unit = {},
    navigateToMain: () -> Unit = {}
) {
    val viewModel: AdjustMacrosViewModel = hiltViewModel()
    val state = viewModel.state
    val userInfoState = viewModel.profile
    val context = LocalContext.current
    val userExists = remember { mutableStateOf(false) }

    LaunchedEffect(userInfoState.value) {
        Log.d("UserInfoState", userInfoState.value.toString())
        if (userInfoState.value.isEmpty()) {
            userExists.value = false
            Log.d("AdjustMacrosScreen", "${userExists.value} ${userInfoState.value}")
            viewModel.onEvent(MacrosEvent.NewUser(userInfoList))
        } else {
            userExists.value = true
            Log.d("AdjustMacrosScreen", "${userExists.value} ${userInfoState.value}")
            viewModel.onEvent(MacrosEvent.ExistingUser(userInfoState.value))
        }
    }

    LaunchedEffect(key1 = state.calories) {
        if (state.calories.isNotEmpty() && state.proteinPercentage.isNotEmpty() && state.carbPercentage.isNotEmpty() && state.fatPercentage.isNotEmpty()) {
            viewModel.onEvent(MacrosEvent.ProteinChanged((state.proteinPercentage.toFloat() * state.calories.toFloat() / 400).toInt().toString()))
            viewModel.onEvent(MacrosEvent.CarbChanged((state.carbPercentage.toFloat() * state.calories.toFloat() / 400).toInt().toString()))
            viewModel.onEvent(MacrosEvent.FatChanged((state.fatPercentage.toFloat() * state.calories.toFloat() / 400).toInt().toString()))
        }
    }
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is AdjustMacrosViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Information has been added",
                        Toast.LENGTH_LONG

                    ).show()
                    navigateToMain()
                }
            }
        }
    }



        val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextKcal = remember { mutableStateOf("") }

    val warningDialogState = remember { mutableStateOf(false) }
    val warningDialogTitle = remember { mutableStateOf("") }
    val warningDialogShowCancelButton = remember { mutableStateOf(true) }
    val warningDialogAlertMessage = remember { mutableStateOf("") }
    val currentOnConfirm = remember { mutableStateOf<() -> Unit>({}) }

    val showInputDialog: (String) -> Unit = { title ->
        inputDialogTitle.value = title
        inputDialogState.value = true
    }

    val showWarningDialog: (String, Boolean, String?, () -> Unit) -> Unit = { title, showCancel, message, onConfirm ->
        warningDialogTitle.value = title
        warningDialogShowCancelButton.value = showCancel
        warningDialogAlertMessage.value = message ?: ""
        currentOnConfirm.value = onConfirm
        warningDialogState.value = true
    }

    val calorieValidator: (String) -> Boolean =
        { calorieAmount -> viewModel.validateCalorieAmount(calorieAmount) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.goals)) },
                navigationIcon = {
                    IconButton(onClick = { navigateToGoals() }) {
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                    showWarningDialog(
                                        "Do you want to continue?",
                                        true,
                                        "Any changes would recalculate your nutrients goals!"
                                    ) {
                                        viewModel.onEvent(MacrosEvent.ResetMacros(userInfoList))
                                    }
                            }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.baseline_change_circle_24),
                                tint = Color(0xFF03A9F4),
                                contentDescription = "Reset Macros"
                            )
                        }
                        IconButton(
                            onClick = {
                                if(!viewModel.validateMacrosPercentages()) {
                                    showWarningDialog(
                                        "Please enter valid values for macronutrients",
                                        false,
                                        "The sum of macronutrient's percentages should be 100%",

                                    ){}
                                }
                                else {
                                    showWarningDialog(
                                        "Do you want to continue?",
                                        true,
                                        "Any changes would recalculate your nutrients goals!"
                                    ) {
                                        viewModel.onEvent(MacrosEvent.SaveMacros(
                                            userInfoList,
                                            userExists.value,
                                            userInfoState.value.getGender(),
                                            userInfoState.value.getActivityLevel(),
                                            userInfoState.value.getGoal(),
                                            userInfoState.value.getAge(),
                                            userInfoState.value.getWeight(),
                                            userInfoState.value.getHeight(),
                                            userInfoState.value.getProtein(),
                                            userInfoState.value.getCarb(),
                                            userInfoState.value.getFat(),
                                            userInfoState.value.getProteinPercentage(),
                                            userInfoState.value.getCarbPercentage(),
                                            userInfoState.value.getFatPercentage(),
                                            userInfoState.value.getCalories()
                                        ))
                                    }
                                }
                            }
                        ) {
                            Icon(
                                ImageVector.vectorResource(id = R.drawable.baseline_save_24),
                                tint = Color(0xFF4CAF50),
                                contentDescription = "Save Macros"
                            )
                        }
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
                Divider(color = colorResource(id = R.color.whiteDelimiter))
                CalorieAdjuster(
                    state = state,
                    onShowInputDialog = { message ->
                        showInputDialog(message)
                        Log.d("Calories after dialog", state.calories)
                    }
                )
                Divider(color = colorResource(id = R.color.whiteDelimiter))
                //NutrientSliders(viewModel, state)

                MacroAdjuster(
                    state = state,
                    viewModel = viewModel,
                    proteinPercentage = state.proteinPercentage.toFloatOrNull() ?: 0f,
                    carbPercentage = state.carbPercentage.toFloatOrNull() ?: 0f,
                    fatPercentage = state.fatPercentage.toFloatOrNull() ?: 0f,
                )
                TipsLayout()

            }
        }
    )
    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = { currentOnConfirm.value() },
        visible = warningDialogState.value,
        showCancelButton = warningDialogShowCancelButton.value,
        alertMessage = warningDialogAlertMessage.value
    )

    TextInputDialog(
        title = inputDialogTitle.value,
        text = inputTextKcal.value,
        onTextChange = { newText -> inputTextKcal.value = newText },
        onDismissRequest = {
            inputDialogState.value = false
            inputTextKcal.value = ""
        },
        onConfirm = {

            viewModel.onEvent(MacrosEvent.CalorieChanged(inputTextKcal.value))
            Log.d("CaloriesChanged", state.calories)
            inputDialogState.value = false
            inputTextKcal.value = ""
        },
        visible = inputDialogState.value,
        validate = calorieValidator,
        validationMessage = "The calorie amount should be an integer!",
        keyboardType = KeyboardType.Number
    )
}
@Composable
fun TipsLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),

    ) {
        Text(
            text = stringResource(id = R.string.macrosTip),
            color = Color(0xFFF17673),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
        )
    }
}