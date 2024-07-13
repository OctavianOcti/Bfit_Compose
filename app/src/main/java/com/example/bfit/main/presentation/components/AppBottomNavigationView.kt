package com.example.bfit.main.presentation.components

import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bfit.main.presentation.utils.BottomNavigationItem


@Composable
fun AppBottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                label = { Text(text = item.title) },
                icon = {
                    BadgedBox(badge = {}) {
                        Icon(
                            imageVector = if (index == selectedIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}
