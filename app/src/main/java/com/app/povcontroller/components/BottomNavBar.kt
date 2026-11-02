package com.app.povcontroller.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.povcontroller.navigation.Screen
import com.app.povcontroller.ui.theme.PrimaryNeon
import com.app.povcontroller.ui.theme.SurfaceDark
import com.app.povcontroller.ui.theme.TextSecondary

@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar(
        modifier = Modifier.height(80.dp),
        containerColor = SurfaceDark,
        contentColor = PrimaryNeon
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Dashboard.route,
            onClick = {
                if (currentRoute != Screen.Dashboard.route) {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Главная") },
            label = { Text("Главная") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryNeon,
                unselectedIconColor = TextSecondary,
                selectedTextColor = PrimaryNeon,
                unselectedTextColor = TextSecondary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Gallery.route,
            onClick = {
                if (currentRoute != Screen.Gallery.route) {
                    navController.navigate(Screen.Gallery.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            },
            icon = { Icon(Icons.Default.Image, contentDescription = "Галерея") },
            label = { Text("Галерея") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryNeon,
                unselectedIconColor = TextSecondary,
                selectedTextColor = PrimaryNeon,
                unselectedTextColor = TextSecondary,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Settings.route,
            onClick = {
                if (currentRoute != Screen.Settings.route) {
                    navController.navigate(Screen.Settings.route) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Настройки") },
            label = { Text("Настройки") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PrimaryNeon,
                unselectedIconColor = TextSecondary,
                selectedTextColor = PrimaryNeon,
                unselectedTextColor = TextSecondary,
                indicatorColor = Color.Transparent
            )
        )
    }
}