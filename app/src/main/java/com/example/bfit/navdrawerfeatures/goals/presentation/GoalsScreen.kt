package com.example.bfit.navdrawerfeatures.goals.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.common.presentation.LogoSection
import com.example.bfit.navdrawerfeatures.common.presentation.AlertDialogWarning
import com.example.bfit.navdrawerfeatures.common.presentation.Divider
import com.example.bfit.navdrawerfeatures.common.presentation.TextInputDialog
import com.example.bfit.navdrawerfeatures.common.presentation.getStringArrayFromResource
import com.example.bfit.util.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalsScreen(
    navigateToMain: () -> Unit = {},
    navigateToMacros: (List<String>) -> Unit = {},
) {
    Log.d("GoalsScreen","Recompose")
    val viewModel: GoalsViewModel = hiltViewModel()
    val state = viewModel.state
    val context = LocalContext.current

    val dialogState = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    val dialogOptions = remember { mutableStateOf(listOf<String>()) }
    val selectedOption = remember { mutableStateOf("") }

    val inputDialogState = remember { mutableStateOf(false) }
    val inputDialogTitle = remember { mutableStateOf("") }
    val inputTextAge = remember { mutableStateOf("") }
    val inputTextWeight = remember { mutableStateOf("") }
    val inputTextHeight = remember { mutableStateOf("") }

    val warningDialogState = remember { mutableStateOf(false) }
    val warningDialogTitle = remember { mutableStateOf("") }
    val warningDialogShowCancelButton = remember { mutableStateOf(true) }
    val warningDialogAlertMessage = remember { mutableStateOf("") }

    val genderOptions = getStringArrayFromResource(R.array.male_famale)
    val activityLevelOptions = getStringArrayFromResource(id = R.array.activity_level)
    val goalOptions = getStringArrayFromResource(id = R.array.weekly_goal)

    val activityLevels = listOf(
        stringResource(id = R.string.sedentary),
        stringResource(id = R.string.slightly_active),
        stringResource(id = R.string.moderately_active),
        stringResource(id = R.string.very_active),
        stringResource(id = R.string.extreme_active)
    )

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

    val ageValidator: (String) -> Boolean = { age -> viewModel.validateAge(age) }
    val weightValidator: (String) -> Boolean = { weight -> viewModel.validateWeight(weight) }
    val heightValidator: (String) -> Boolean = { height -> viewModel.validateHeight(height) }

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is GoalsViewModel.ValidationEvent.Success -> {
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.goals)) },
                navigationIcon = {
                    IconButton(onClick = { navigateToMain() }) {
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
                        if (viewModel.validateInputData(context, state.gender, state.weight, state.height, state.age, state.activityLevel, state.goal)) {
                        // showWarningDialog("Please fill in all the required information!",false,"")
                        showWarningDialog("Do you want to continue?", true, "Any changes would recalculate your nutrients goals!")
                            //viewModel.setProfileDocument()
                           // viewModel.onEvent(GoalsEvent.Submit)
                        } else {
                            showWarningDialog("Please fill in all the required information!", false, "Please ensure all fields are filled out correctly before submitting.")
                        }
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
                    text = stringResource(id = R.string.enter_your_stats_below),
                    color = colorResource(id = R.color.orange),
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                LayoutWithFields(
                    state = state,
                    onGenderClick = { showDialog("Select your gender", genderOptions) },
                    onActivityLevelClick = { showDialog("Select your activity level", activityLevelOptions) },
                    onGoalClick = { showDialog("Select your goal", goalOptions) },
                    onAgeClick = { showInputDialog("Enter your age") },
                    onWeightClick = { showInputDialog("Enter your weight") },
                    onHeightClick = { showInputDialog("Enter your height") }
                )

                LayoutForGoals(navigateToMacros,viewModel,context,state)

                TipsLayout()

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
                "Select your gender" -> viewModel.onEvent(GoalsEvent.GenderChanged(selectedOption.value))
                "Select your activity level" -> {
                    var activityLevel = ""
                    when (selectedOption.value) {
                        activityLevelOptions[0] -> activityLevel = activityLevels[0]
                        activityLevelOptions[1] -> activityLevel = activityLevels[1]
                        activityLevelOptions[2] -> activityLevel = activityLevels[2]
                        activityLevelOptions[3] -> activityLevel = activityLevels[3]
                        activityLevelOptions[4] -> activityLevel = activityLevels[4]
                    }
                    viewModel.onEvent(GoalsEvent.ActivityLevelChanged(activityLevel))
                }
                "Select your goal" -> viewModel.onEvent(GoalsEvent.GoalChanged(selectedOption.value))
            }
            dialogState.value = false
        },
        visible = dialogState.value
    )

    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = { viewModel.setProfileDocument() },
        //onConfirm = { Log.d("GoalsScreen","onConfirm")},
        visible = warningDialogState.value,
        showCancelButton = warningDialogShowCancelButton.value,
        alertMessage = warningDialogAlertMessage.value
    )

    TextInputDialog(
        title = inputDialogTitle.value,
        text = when (inputDialogTitle.value) {
            "Enter your age" -> inputTextAge.value
            "Enter your weight" -> inputTextWeight.value
            "Enter your height" -> inputTextHeight.value
            else -> ""
        },
        onTextChange = {
            when (inputDialogTitle.value) {
                "Enter your age" -> inputTextAge.value = it
                "Enter your weight" -> inputTextWeight.value = it
                "Enter your height" -> inputTextHeight.value = it
            }
        },
        onDismissRequest = {
            inputDialogState.value = false
            inputTextAge.value= ""
            inputTextWeight.value=""
            inputTextHeight.value= ""
        },
        onConfirm = {
            when (inputDialogTitle.value) {
                "Enter your age" -> viewModel.onEvent(GoalsEvent.AgeChanged(inputTextAge.value))
                "Enter your weight" -> viewModel.onEvent(GoalsEvent.WeightChanged(inputTextWeight.value))
                "Enter your height" -> viewModel.onEvent(GoalsEvent.HeightChanged(inputTextHeight.value))
            }
            inputTextAge.value= ""
            inputTextWeight.value=""
            inputTextHeight.value= ""
            inputDialogState.value = false
        },
        visible = inputDialogState.value,
        validate = when (inputDialogTitle.value) {
            "Enter your age" -> ageValidator
            "Enter your weight" -> weightValidator
            "Enter your height" -> heightValidator
            else -> { _: String -> true }
        },
        validationMessage = when (inputDialogTitle.value) {
            "Enter your age" -> "Age must be between ${Constants.MIN_AGE} and ${Constants.MAX_AGE}."
            "Enter your weight" -> "Weight must be between ${Constants.MIN_WEIGHT}kg and ${Constants.MAX_WEIGHT}kg (maximum one decimal allowed)"
            "Enter your height" -> "Height must be between ${Constants.MIN_HEIGHT}cm and ${Constants.MAX_HEIGHT}cm (maximum one decimal allowed)"
            else -> ""
        }
    )
}


@Composable
fun LayoutWithFields(
    state : GoalsState,
    onGenderClick: () -> Unit,
    onActivityLevelClick: () -> Unit,
    onGoalClick: () -> Unit,
    onAgeClick: () -> Unit,
    onWeightClick: () -> Unit,
    onHeightClick: () -> Unit
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
                label = stringResource(id = R.string.gender),
                value = state.gender.ifEmpty { stringResource(id = R.string.set_gender) },
                onFieldClick = onGenderClick
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.weight_kgs),
                value = state.weight.ifEmpty { stringResource(id = R.string.set_current_weight) },
                onFieldClick = onWeightClick
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.height_cm),
                value = state.height.ifEmpty { stringResource(id = R.string.set_current_height) },
                onFieldClick = onHeightClick
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.age_years),
                value = state.age.ifEmpty { stringResource(id = R.string.set_current_age) },
                onFieldClick = onAgeClick
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.activity_level),
                value = state.activityLevel.ifEmpty { stringResource(id = R.string.set_current_activity_level) },
                onFieldClick = onActivityLevelClick
            )
            Divider()
            FieldRow(
                label = stringResource(id = R.string.goal),
                value = state.goal.ifEmpty { stringResource(id = R.string.set_your_goal) },
                onFieldClick = onGoalClick
            )
        }
    }
}

@Composable
fun LayoutForGoals(
    navigateToMacros: (List<String>) -> Unit,
    viewModel: GoalsViewModel,
    context: Context,
    state: GoalsState) {
    val warningDialogState = remember { mutableStateOf(false) }
    val warningDialogTitle = remember { mutableStateOf("") }
    val warningDialogShowCancelButton = remember { mutableStateOf(true) }
    val warningDialogAlertMessage = remember { mutableStateOf("") }

    val showWarningDialog: (String, Boolean, String?) -> Unit = { title, showCancel, message ->
        warningDialogTitle.value = title
        warningDialogShowCancelButton.value = showCancel
        warningDialogAlertMessage.value = message ?: ""
        warningDialogState.value = true
    }
    val coroutineScope = rememberCoroutineScope()
    val shakeOffset = remember { Animatable(0f) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.nutrition_goals),
            color = colorResource(id = R.color.darkWhite),
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 20.dp, bottom = 2.dp)
        )
        Divider(color = Color(0xFFFFFFFF))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.ic_bfit_logo_background))
                .padding(vertical = 10.dp)

                .clickable {
                    coroutineScope.launch {
                        shakeOffset.animateTo(
                            targetValue = 15f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = -15f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = 10f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = -10f,
                            animationSpec = tween(durationMillis = 50)
                        )
                        shakeOffset.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = 50)
                        )
                    }
                    if (!viewModel.validateInputData(context, state.gender, state.weight, state.height, state.age, state.activityLevel, state.goal)) {
                        showWarningDialog(
                            "Please fill in all the required information!",
                            false,
                            "Please ensure all fields are filled out correctly before submitting."
                        )
                    } else {
                        navigateToMacros(listOf(state.gender,state.activityLevel,state.goal,state.age,state.weight,state.height))
                    }
                }
                .offset(x = shakeOffset.value.dp)
        ) {
            Text(
                text = stringResource(id = R.string.calorie_carbs_protein_and_fat_goals),
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.adjust_your_default_or_daily_goals),
                color = colorResource(id = R.color.whiteDelimiter),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.you_should_set_your_personal_information_first),
                color = Color(0xFF4CAF50),
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)
            )
        }
    }

    AlertDialogWarning(
        title = warningDialogTitle.value,
        onDismissRequest = { warningDialogState.value = false },
        onConfirm = {  },
        //onConfirm = { Log.d("GoalsScreen","onConfirm")},
        visible = warningDialogState.value,
        showCancelButton = warningDialogShowCancelButton.value,
        alertMessage = warningDialogAlertMessage.value
    )
}

@Composable
fun TipsLayout() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.GoalsTip),
            color = Color(0xFFF17673),
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun FieldRow(label: String, value: String, onFieldClick: () -> Unit) {
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
                color = colorResource(id = R.color.blueForDarkGrey),
                textAlign = TextAlign.End
            )
        }
    }
}




