package com.example.bfit.navdrawerfeatures.addFood

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.bfit.R
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun AddFoodScreen(
    navigateToDiary: () -> Unit = {},
    navigateToQuickAdd: () -> Unit = {}
) {
    Scaffold(
        topBar = { AddFoodTopBar(navigateToDiary) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.darkGrey))
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            item {
                QuickAddSection(navigateToQuickAdd)
            }
            item {
                FoodMealSection()
            }
            items(10) { // Assuming you have 10 items, adjust accordingly
                ApiCard()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodTopBar(navigateToDiary: () -> Unit) {
    TopAppBar(
        title = {
            ConstraintLayout(
                modifier = Modifier.fillMaxWidth()
            ) {
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
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .constrainAs(searchField) {
                            start.linkTo(backIcon.end, margin = 0.dp)
                            end.linkTo(scanIcon.start, margin = 10.dp)
                            top.linkTo(parent.top, margin = 10.dp)
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                            width = Dimension.fillToConstraints
                        }
                        .height(56.dp)  // Set a proper height for the TextField
                        .padding(horizontal = 8.dp),  // Add padding inside the TextField
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
                    shape = RoundedCornerShape(20.dp),  // Adjust shape if needed for smaller height
                    singleLine = true,
                    maxLines = 1
                )

                Image(
                    painter = painterResource(id = R.drawable.search_barcode_icon),
                    contentDescription = stringResource(id = R.string.imgscanbarcode),
                    modifier = Modifier
                        .size(48.dp) // Set the desired size for the icon
                        .constrainAs(scanIcon) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end, margin = 15.dp)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable { /* TODO: Handle barcode scan action */ }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.ic_bfit_logo_background),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        actions = {
            // Add any actions if needed
        },
    )
}
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

@Composable
fun QuickAddSection(onClick: () -> Unit) {
    var isShaking by remember { mutableStateOf(false) }
    val shakeModifier = shakeAnimation(
        modifier = Modifier,
        onShake = {
            // Optionally trigger any additional logic here

            isShaking = true
            onClick()
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
            modifier = Modifier
                .size(40.dp)
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
                .clip(RoundedCornerShape(16.dp)),
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
                .clip(RoundedCornerShape(16.dp)),
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


