@file:OptIn(ExperimentalMaterial3Api::class)

package com.wdevs.simplethings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wdevs.simplethings.ui.MainScreen
import com.wdevs.simplethings.ui.Navigation
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Preview(showBackground = true, widthDp = 220, heightDp = 400)
@Composable
fun DefaultPreview() {
    Navigation()
}