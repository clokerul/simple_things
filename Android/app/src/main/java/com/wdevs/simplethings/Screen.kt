package com.wdevs.simplethings

sealed class Screen(val route: String) {
    object MainScreen : Screen("mainScreen")
    object TheListScreen : Screen("theListScreen")
    object MyListScreen : Screen("myListScreen")
}
