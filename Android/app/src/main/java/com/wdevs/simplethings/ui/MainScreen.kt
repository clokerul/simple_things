package com.wdevs.simplethings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wdevs.simplethings.Screen

@Composable
fun MainScreen(navController: NavController) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.LightGray)
        ) {
            val modifier = Modifier
            Header(text = "George")
            Menu(
                modifier = Modifier.weight(1F),
                onClick = { destination: String -> navController.navigate(destination); })
            Footer(text = "whoami")
        }
    }
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Composable
fun Menu(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    var t = 5;
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        MenuCard(
            text = "THE LIST",
            onClick = {onClick(Screen.MyListScreen.route)},
        )
        MenuCard(
            text = "YOUR LIST",
            onClick = {onClick(Screen.MyListScreen.route)}
        )
    }
}


@Composable
fun MenuCard(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text, modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 0.dp, vertical = 15.dp)
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .fillMaxWidth()
                .background(color = Color.Gray.copy(alpha = 0.3f))
        )
    }
}

@Composable
fun Footer(text: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(text)
    }
}