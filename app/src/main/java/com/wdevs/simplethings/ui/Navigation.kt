package com.wdevs.simplethings.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wdevs.simplethings.Screen

@Composable
fun Navigation() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}