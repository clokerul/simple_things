package com.wdevs.simplethings.feature.startscreen

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import com.wdevs.simplethings.core.data.quotes.OFQuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.di.ApiModule
import com.wdevs.simplethings.core.network.NetworkDataSource
import com.wdevs.simplethings.ui.destinations.MyListScreenDestination
import com.wdevs.simplethings.ui.destinations.TheListScreenDestination
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(navigator: DestinationsNavigator) {
    // A surface container using the 'background' color from the theme
    val context = LocalContext.current
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
                text = "George",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Body(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                onClick = { direction: Direction -> navigator.navigate(direction) })
            Footer(modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(context, "to be implemented", Toast.LENGTH_LONG)
                        .show()
                }, text = "whoami")
        }
    }
}

@Composable
fun Header(text: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier
    ) {
        Text(text = text)
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
            modifier = modifierMenuCard,
            text = "THE LIST",
            onClick = {
                onClick(TheListScreenDestination) },
        )
        MenuCard(
            modifier = modifierMenuCard,
            text = "YOUR LIST",
            onClick = { onClick(MyListScreenDestination) }
        )
    }
}

@Composable
fun MenuCard(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val navController = rememberNavController()
    Box(
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text, modifier = Modifier
                .align(Alignment.Center)
        )
        Spacer(
            modifier = Modifier
                .matchParentSize()
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

@Composable
//@Preview(heightDp = 1000)
fun PreviewStartScreen() {
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
                text = "George",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Body(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                onClick = {  })
            Footer(modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .clickable {
                }, text = "whoami")
        }
    }
}