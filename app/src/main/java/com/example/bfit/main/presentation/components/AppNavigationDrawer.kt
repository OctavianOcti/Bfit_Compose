package com.example.bfit.main.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.bfit.R
import com.example.bfit.util.navigation.Home
import com.example.bfit.util.navigation.Profile
import com.example.bfit.main.presentation.utils.NavigationItem
import kotlinx.coroutines.launch

@Composable
fun AppNavigationDrawer(
    items: List<NavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    drawerState: DrawerState,
    navigateToLogin: () -> Unit,
    navigateToGoals: () -> Unit,
    navigateToDiary: () -> Unit,
    onLogout: () -> Unit,
    navController: NavController,
    selectedBottomItemIndex: Int,
    onBottomItemSelected: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalDrawerSheet(
       drawerContainerColor = colorResource(id = R.color.darkGrey),
       // drawerContentColor = colorResource(id = R.color.darkWhite)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        items.forEachIndexed { index, item ->
            Column {
                NavigationDrawerItem(
                    label = { Text(text = item.title, color = colorResource(id = R.color.whiteDelimiter)) },
                    selected = index == selectedIndex,
                    onClick = {
                        if (item.title == "Logout") {
                            onLogout() // Call the logout callback
                            navigateToLogin()
                        }
                        else if (item.title == "Profile") {
                            onItemSelected(index)
                            onBottomItemSelected(index)
                            navController.navigate(Profile) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        else  if (item.title == "Home") {
                            onItemSelected(index)
                            onBottomItemSelected(index)
                            navController.navigate(Home) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        else if  (item.title == "Goals") {
                            navigateToGoals()
                        }
                        else if(item.title == "Diary"){
                            navigateToDiary()
                        }
                        else {
                            onItemSelected(index)
                        }
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title,
                            tint = colorResource(id = R.color.orange)
                        )
                    },
                    badge = {},
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                if (item.title == "Meals & Food") {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }
}
