package com.example.bfit.navdrawerfeatures.profile.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.main.domain.model.DataProvider
import com.example.bfit.main.domain.model.UserInfo
import com.example.bfit.navdrawerfeatures.common.presentation.FooterSection
import com.example.bfit.navdrawerfeatures.common.presentation.LogoSection
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navigateToGoals : () -> Unit = {},
    navigateToMacros: (List<String>) -> Unit = {},
    navigateToDiary : () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val viewModel : ProfileViewModel = hiltViewModel()
    val userInfo = viewModel.profile.value
    val openLoginDialog = remember { mutableStateOf(false) }
   // val authState = DataProvider.authState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.darkGrey))
            .verticalScroll(scrollState)
    ) {
        UserInfoSection(userInfo)
        CustomizationSection(navigateToGoals,navigateToMacros,navigateToDiary,userInfo)
        LogoSection()
        FooterSection()
    }
}

@Composable
fun UserInfoSection(userInfo: UserInfo ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.ic_bfit_logo_background))
                .padding(10.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 25.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp, 100.dp)
                            .padding(end = 20.dp)
                    )
                    Column {
                        Text(
                            //text = stringResource(id = R.string.username),
                            text = DataProvider.user?.email ?: "Email Placeholder",
                            color = Color.White,
                            fontSize = 16.sp,
                            maxLines = 2
                        )
                        Row {
                            Text(
                                text = userInfo.getAge().toString() ,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = stringResource(id = R.string.years_old),
                                color = Color.White,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                            Text(
                                text = userInfo.getGender(),
                                color = Color.White,
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )

                UserInfoDetail(
                    iconResId = R.drawable.scale,
                    label = stringResource(id = R.string.weight),
                    value = userInfo.getWeight().toString(),
                    unit = stringResource(id = R.string.kg)
                )
                UserInfoDetail(
                    iconResId = R.drawable.height,
                    label = stringResource(id = R.string.height),
                    value = userInfo.getHeight().toString(),
                    unit = stringResource(id = R.string.cm)
                )
                UserInfoDetail(
                    iconResId = R.drawable.goal,
                    label = stringResource(id = R.string.goal),
                    value = userInfo.getGoal(),
                    unit = ""
                )
                UserInfoDetail(
                    iconResId = R.drawable.activity_level,
                    label = stringResource(id = R.string.activity_level),
                    value = userInfo.getActivityLevel(),
                    unit = ""
                )
            }
        }
    }
}

@Composable
fun UserInfoDetail(iconResId: Int, label: String, value: String, unit: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 20.dp)
        )
        Column {
            Text(
                text = label,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Row {
                Text(
                    text = value,
                    color = Color.White
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        color = Color.White,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun CustomizationDetail(iconResId: Int, label: String, description: String, onClick: () -> Unit) {
    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
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
                    onClick()
                })
            }
            .offset(x = shakeOffset.value.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 20.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = Color(0xFFBDBDBD),
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    color = Color(0xFFBDBDBD),
                    fontSize = 14.sp
                )
            }
        }
        Text(
            text = ">",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 25.dp)
        )
    }
}

@Composable
fun CustomizationSection(
    navigateToGoals: () -> Unit,
    navigateToMacros: (List<String>) -> Unit,
    navigateToDiary: () -> Unit,
    userInfo: UserInfo
)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.customization),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.orange),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.ic_bfit_logo_background))
                .padding(10.dp)
        ) {
            Column {
                CustomizationDetail(
                    iconResId = R.drawable.user_yellow_icon,
                    label = stringResource(id = R.string.personal_details),
                    description = "",
                    onClick = { navigateToGoals()}
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                CustomizationDetail(
                    iconResId = R.drawable.baseline_edit_24,
                    label = stringResource(id = R.string.adjust_calories),
                    description = stringResource(id = R.string.protein_carbs_and_fat),
                    onClick = {
                        if(userInfo.isEmpty()) navigateToMacros(listOf("","","","","",""))
                       else  navigateToMacros(listOf(userInfo.getGender(),userInfo.getActivityLevel(),userInfo.getGoal(),userInfo.getAge().toString(),userInfo.getWeight().toString(),userInfo.getHeight().toString())) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                CustomizationDetail(
                    iconResId = R.drawable.baseline_set_meal_24,
                    label = stringResource(id = R.string.adjust_meals_and_food),
                    description = "",
                    onClick = { /* Handle Adjust Meals and Food click */ }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                CustomizationDetail(
                    iconResId = R.drawable.id_diary_yellow,
                    label = stringResource(id = R.string.track_food),
                    description = "",
                    onClick = { navigateToDiary() }
                )
            }
        }
    }
}




