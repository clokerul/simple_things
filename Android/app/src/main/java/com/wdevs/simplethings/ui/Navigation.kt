package com.wdevs.simplethings.ui

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.wdevs.simplethings.feature.NavGraphs

@Composable
fun Navigation() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}