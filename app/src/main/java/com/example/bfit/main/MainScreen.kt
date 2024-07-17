package com.example.bfit.main

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bfit.navdrawerfeatures.HomeScreen
import com.example.bfit.R
import com.example.bfit.util.navigation.Home
import com.example.bfit.util.navigation.Profile
import com.example.bfit.main.presentation.components.AppNavigationDrawer
import com.example.bfit.main.presentation.AuthViewModel
import com.example.bfit.main.presentation.components.AppTopAppBar
import com.example.bfit.main.presentation.utils.BottomNavigationItem
import com.example.bfit.main.presentation.utils.NavigationItem
import com.example.bfit.navdrawerfeatures.profile.presentation.ProfileScreen


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigateToLogin : () -> Unit = {},
    navigateToGoals : () -> Unit = {}

) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val navController = rememberNavController()

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
        BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home, Home),
        BottomNavigationItem("Profile", Icons.Filled.AccountBox, Icons.Outlined.AccountBox, Profile)
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
                    navigateToGoals = {navigateToGoals()},
                    onLogout = { authViewModel.signOut() },
                    navController = navController,
                    selectedBottomItemIndex = selectedBottomItemIndex,
                    onBottomItemSelected = { index -> selectedBottomItemIndex = index }
                )
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = { AppTopAppBar(drawerState) },
                bottomBar = {
                    NavigationBar(
                        containerColor = colorResource(id = R.color.orange)
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination


                        bottomNavItems.forEachIndexed {index, navItem ->
                            NavigationBarItem(selected = currentDestination?.hierarchy?.any{it.route == navItem.route} == true,
                                onClick = {
                                    selectedBottomItemIndex = index
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id){
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedBottomItemIndex) {
                                            navItem.selectedIcon
                                        } else navItem.unselectedIcon,
                                        contentDescription = navItem.title
                                    )
                                },

                                label = {
                                    Text(text = navItem.title)

                                }
                            )
                        }
                    }

                }

            ) { paddingValues ->
                // Apply paddingValues to your content to account for the top bar
//                        Column(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(paddingValues)
//                        ) {
//                            ProfileScreen()
//                        }
                NavHost(
                    navController = navController,
                    startDestination = Home,
                    modifier = Modifier
                        .padding(paddingValues)

                ) {
                    composable<Home> {
                        HomeScreen()
                    }
                    composable<Profile> {
                        ProfileScreen { navigateToGoals() }
                    }
                }
            }
        }
    }
}
//}
