package com.example.bfit.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bfit.R
import com.example.bfit.main.presentation.AppNavigationDrawer
import com.example.bfit.main.presentation.AuthViewModel
import com.example.bfit.main.presentation.components.AppBottomNavigationBar
import com.example.bfit.main.presentation.components.AppTopAppBar
import com.example.bfit.main.presentation.utils.BottomNavigationItem
import com.example.bfit.main.presentation.utils.NavigationItem
import com.example.bfit.profile.presentation.ProfileScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigateToLogin : () -> Unit = {}

) {
    val authViewModel: AuthViewModel = hiltViewModel()
   // val authState by authViewModel.authState.collectAsState()

    //val currentUser = authViewModel.currentUser.collectAsState().value
    //DataProvider.updateAuthState(currentUser)
    ////val authState = DataProvider.authState

//    val (authState, setAuthState) = remember { mutableStateOf(DataProvider.authState) }
//    LaunchedEffect(currentUser) {
//        DataProvider.updateAuthState(currentUser)
//        setAuthState(DataProvider.authState)
//    }


        //if (authState != AuthState.SignedIn) {
            //authViewModel.signOut()
            //navigateToLogin()
       // } else {

//            when (authState) {
//                AuthState.SignedIn -> Log.d("MainScreen", "Singned in")
//                AuthState.Authenticated -> Log.d("MainScreen", "Authenticated")
//                AuthState.SignedOut -> Log.d("MainScreen", "Signed out")
//            }


            val navDrawerItems = listOf(
                NavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
                NavigationItem("Profile", Icons.Filled.AccountBox, Icons.Outlined.AccountBox),
                NavigationItem(
                    "Goals",
                    ImageVector.vectorResource(id = R.drawable.nav_goals),
                    ImageVector.vectorResource(id = R.drawable.nav_goals)
                ),
                NavigationItem(
                    "Diary",
                    ImageVector.vectorResource(id = R.drawable.ic_diary),
                    ImageVector.vectorResource(id = R.drawable.ic_diary)
                ),
                NavigationItem(
                    "Meals & Food",
                    ImageVector.vectorResource(id = R.drawable.baseline_meals),
                    ImageVector.vectorResource(id = R.drawable.baseline_meals)
                ),
                NavigationItem(
                    "Logout",
                    Icons.AutoMirrored.Filled.ExitToApp,
                    Icons.AutoMirrored.Outlined.ExitToApp
                )
            )

            val bottomNavItems = listOf(
                BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
                BottomNavigationItem("Profile", Icons.Filled.AccountBox, Icons.Outlined.AccountBox)
            )

            var selectedNavItemIndex by rememberSaveable { mutableIntStateOf(0) }
            var selectedBottomItemIndex by rememberSaveable { mutableIntStateOf(0) }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ModalNavigationDrawer(
                    drawerContent = {
                        AppNavigationDrawer(
                            items = navDrawerItems,
                            selectedIndex = selectedNavItemIndex,
                            onItemSelected = { selectedNavItemIndex = it },
                            drawerState = drawerState,
                            navigateToLogin = { navigateToLogin() },
                            onLogout = { authViewModel.signOut() }
                        )
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = { AppTopAppBar(drawerState) },
                        bottomBar = {
                            AppBottomNavigationBar(
                                items = bottomNavItems,
                                selectedIndex = selectedBottomItemIndex,
                                onItemSelected = { selectedBottomItemIndex = it }
                            )
                        }
                    ) { paddingValues ->
                        // Apply paddingValues to your content to account for the top bar
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    //}





