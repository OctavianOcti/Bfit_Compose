package com.example.bfit.navdrawerfeatures.addFood.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.navdrawerfeatures.showMealsFood.domain.FoodInfoModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@Composable
fun AddFoodScreen(
    formattedDate: String,
    navigateToDiary: () -> Unit = {},
    navigateToQuickAdd: (String) -> Unit = {},
    navigateToApiFoodInfo: (FoodInfoModel, String, String) -> Unit = { _, _, _ -> }
) {
    val viewModel: AddFoodViewModel = hiltViewModel()
    val foodInfoState by viewModel.foodInfoState.collectAsState()
    val state = viewModel.state
    var showCameraPreview by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(key1 = formattedDate) {
        Log.d("AddFoodScreen date", formattedDate)
    }

    Scaffold(
        topBar = {
            AddFoodTopBar(navigateToDiary, viewModel, state) {
                showCameraPreview = it
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(colorResource(id = R.color.darkGrey)) // Ensure background is consistent
            ) {
                if (showCameraPreview) {
                    BarcodeScanner(
                        showCameraPreview = showCameraPreview,
                        onDismiss = { showCameraPreview = false },
                        onBarcodeScanned = { scannedBarcode ->
                            viewModel.onEvent(AddFoodEvent.SearchByBarcode(scannedBarcode))
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp) // Adjust padding as needed
                    ) {
                        item {
                            QuickAddSection(navigateToQuickAdd, formattedDate)
                        }
                        item {
                            FoodMealSection()
                        }
                        items(foodInfoState) { foodInfo ->
                            ApiCard(
                                foodInfoModel = foodInfo,
                                onCLick = { navigateToApiFoodInfo(foodInfo, context.getString(R.string.select_meal), formattedDate) }
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddFoodTopBar(
    navigateToDiary: () -> Unit,
    viewModel: AddFoodViewModel,
    state: AddFoodState,
    onCameraPreviewToggle: (Boolean) -> Unit
) {
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(key1 = true) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }

    TopAppBar(
        title = {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (backIcon, searchField, scanIcon) = createRefs()

                IconButton(
                    onClick = { navigateToDiary() },
                    modifier = Modifier.constrainAs(backIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "NavigateToMain"
                    )
                }

                TextField(
                    value = state.text,
                    onValueChange = { viewModel.onEvent(AddFoodEvent.SearchTextChanged(it)) },
                    modifier = Modifier
                        .constrainAs(searchField) {
                            start.linkTo(backIcon.end, margin = 0.dp)
                            end.linkTo(scanIcon.start, margin = 10.dp)
                            top.linkTo(parent.top, margin = 10.dp)
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                            width = Dimension.fillToConstraints
                        }
                        .height(56.dp)
                        .padding(horizontal = 8.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = colorResource(id = R.color.darkGrey),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = colorResource(id = R.color.blueForDarkGrey),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = colorResource(id = R.color.darkGrey),
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search_for_food_meal),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onEvent(AddFoodEvent.SearchForFood(state.text))
                        }
                    )
                )

                Image(
                    painter = painterResource(id = R.drawable.search_barcode_icon),
                    contentDescription = stringResource(id = R.string.imgscanbarcode),
                    modifier = Modifier
                        .size(48.dp)
                        .constrainAs(scanIcon) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end, margin = 15.dp)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            if (cameraPermission.status.isGranted) {
                                onCameraPreviewToggle(true)
                            } else {
                                cameraPermission.launchPermissionRequest()
                            }
                        }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.ic_bfit_logo_background),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}


@Composable
fun BarcodeScanner(
    showCameraPreview: Boolean,
    onDismiss: () -> Unit,
    onBarcodeScanned: (String) -> Unit
) {
    val camera = remember { BarcodeCamera() }
    var lastScannedBarcode by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (showCameraPreview) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            val width = canvasWidth * .9f
                            val height = width * 3 / 4f

                            drawContent()

                            drawRect(Color(0x99000000))

                            drawRoundRect(
                                topLeft = Offset(
                                    (canvasWidth - width) / 2,
                                    canvasHeight * .3f
                                ),
                                size = Size(width, height),
                                color = Color.Transparent,
                                cornerRadius = CornerRadius(24.dp.toPx()),
                                blendMode = BlendMode.SrcIn
                            )

                            drawRoundRect(
                                topLeft = Offset(
                                    (canvasWidth - width) / 2,
                                    canvasHeight * .3f
                                ),
                                color = Color.White,
                                size = Size(width, height),
                                cornerRadius = CornerRadius(24.dp.toPx()),
                                style = Stroke(
                                    width = 2.dp.toPx()
                                ),
                                blendMode = BlendMode.Src
                            )
                        }
                ) {
                    camera.CameraPreview(
                        onBarcodeScanned = { barcode ->
                            barcode?.displayValue?.takeIf { it.isNotBlank() }?.let { scannedBarcode ->
                                if (scannedBarcode != lastScannedBarcode) {
                                    lastScannedBarcode = scannedBarcode
                                    Log.d("BarcodeScanner", "Scanned Barcode: $scannedBarcode")
                                    onDismiss()
                                    onBarcodeScanned(scannedBarcode)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun QuickAddSection(onClick: (String) -> Unit, formattedDate: String) {
    var isShaking by remember { mutableStateOf(false) }
    val shakeModifier = shakeAnimation(
        modifier = Modifier,
        onShake = {
            isShaking = true
            onClick(formattedDate)
        }
    )
    val clickableModifier = Modifier
        .clickable {
            isShaking = true
        }
        .then(shakeModifier)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = clickableModifier
            .padding(start = 15.dp, top = 12.dp, bottom = 5.dp, end = 15.dp)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.ic_bfit_logo_background),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painterResource(id = R.drawable.quick_add_calories),
            contentDescription = stringResource(id = R.string.imgmanualaddfood),
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.quick_add),
                color = colorResource(id = R.color.darkWhite),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun FoodMealSection() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .height(120.dp)
            .background(Color(0xFFE4AB38))
    ) {
        val (createFood, createMeal) = createRefs()
        val guideline = createGuidelineFromStart(0.5f)

        Box(
            modifier = Modifier
                .constrainAs(createFood) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 15.dp)
                    end.linkTo(guideline, margin = 15.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(10.dp)
                .background(
                    colorResource(id = R.color.ic_bfit_logo_background),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.watermelon),
                    contentDescription = stringResource(id = R.string.imgwatermelon),
                    modifier = Modifier
                        .size(80.dp, 60.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.create_a_food),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.whiteDelimiter)
                )
            }
        }
        Box(
            modifier = Modifier
                .constrainAs(createMeal) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(guideline, margin = 15.dp)
                    end.linkTo(parent.end, margin = 15.dp)
                    width = Dimension.fillToConstraints
                }
                .padding(10.dp)
                .background(
                    colorResource(id = R.color.ic_bfit_logo_background),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.meal),
                    contentDescription = stringResource(id = R.string.imgmeal),
                    modifier = Modifier
                        .size(80.dp, 60.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.create_a_meal),
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.whiteDelimiter)
                )
            }
        }
    }
}

@SuppressLint("ModifierFactoryExtensionFunction")
@Composable
fun shakeAnimation(
    modifier: Modifier = Modifier,
    onShake: () -> Unit = {}
): Modifier {
    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    fun shake() {
        coroutineScope.launch {
            shakeOffset.animateTo(
                targetValue = 16f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = -16f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = 8f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = -8f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
            )
            onShake()
        }
    }

    return modifier
        .graphicsLayer {
            translationX = shakeOffset.value
        }
        .pointerInput(Unit) {
            detectTapGestures(onTap = { shake() })
        }
}