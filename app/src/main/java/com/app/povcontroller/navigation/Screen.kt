package com.app.povcontroller.navigation

sealed class Screen(val route: String) {
    object Scan : Screen("scan_screen")
    object Dashboard : Screen("dashboard_screen")
    object Gallery : Screen("gallery_screen")
    object Settings : Screen("settings_screen")
}