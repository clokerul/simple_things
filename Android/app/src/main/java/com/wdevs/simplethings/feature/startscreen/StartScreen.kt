package com.wdevs.simplethings.feature.startscreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.wdevs.simplethings.feature.destinations.MyListScreenDestination
import com.wdevs.simplethings.feature.destinations.TheListScreenDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun StartScreen(
    navigator: DestinationsNavigator,
    startScreenViewModel: StartScreenViewModel = hiltViewModel()
) {
    // A surface container using the 'background' color from the theme
    val context = LocalContext.current
    val startScreenUiState by startScreenViewModel.uiState.collectAsStateWithLifecycle()
    StartScreenStateless(
        uiState = startScreenUiState,
        onMenuClick = { direction: Direction -> navigator.navigate(direction) },
        onUsernameChange = startScreenViewModel::onUsernameChange
    )
}

@Composable
fun StartScreenStateless(
    uiState: StartScreenUiState,
    onMenuClick: (Direction) -> Unit,
    onUsernameChange: (username: String) -> Unit,
) {
    when (uiState) {
        is StartScreenUiState.Success -> {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ) {
                    Header(
                        username = uiState.username ?: "",
                        modifier = Modifier
                            .fillMaxWidth(),
                        onUsernameChange = onUsernameChange
                    )
                    Body(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxSize(),
                        onClick = onMenuClick
                    )
                    Footer(
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(),
                        text = "whoami"
                    )
                }
            }
        }
    }
    // A surface container using the 'background' color from the theme
    val context = LocalContext.current

}

@Composable
fun Header(
    username: String,
    modifier: Modifier = Modifier,
    onUsernameChange: (username: String) -> Unit,
) {
    var text by remember { mutableStateOf(username) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Row {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it.replace("\n", "").substring(0, minOf(it.length, 20))
                    onUsernameChange(text)
                },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun Body(modifier: Modifier = Modifier, onClick: (Direction) -> Unit) {
    var t = 5;
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        val modifierMenuCard = modifier.padding(horizontal = 0.dp, vertical = 12.dp)
        MenuCard(
            modifier = modifierMenuCard.offset(y = 100.dp),
            text = "THE LIST",
            onClick = {
                onClick(TheListScreenDestination)
            },
        )
        MenuCard(
            modifier = modifierMenuCard.offset(y = (-100).dp),
            text = "MY LIST",
            onClick = { onClick(MyListScreenDestination) }
        )
    }
}

@Composable
fun Footer(text: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(context, "to be implemented", Toast.LENGTH_LONG)
                    .show()
            }
    ) {
        Text(text)
    }
}

@Composable
fun MenuCard(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val navController = rememberNavController()
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.Gray.copy(0.3f))
                .clickable { onClick() },
        )
        Text(
            text,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
@Preview(heightDp = 800, widthDp = 400)
fun PreviewStartScreen() {
    StartScreenStateless(
        uiState = StartScreenUiState.Success("George"),
        onMenuClick = {},
        onUsernameChange = {}
    )
}