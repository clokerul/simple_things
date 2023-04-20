package com.wdevs.simplethings.feature.thelist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuoteResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wdevs.simplethings.R

@Destination
@Composable
fun TheListScreen(
    navController: NavController,
    theListViewModel: TheListViewModel = hiltViewModel()
) {
    val theListUiState by theListViewModel.uiState.collectAsStateWithLifecycle()
    TheListScreenStateless(uiState = theListUiState)
}

@Composable
fun TheListScreenStateless(uiState: TheListUiState) {
    when (uiState) {
        TheListUiState.Loading -> Text("Loading")
        is TheListUiState.Success -> {
            QuotesFeed(uiState.quotesList)
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Button(modifier = Modifier.align(Alignment.BottomEnd), onClick = {}) {
                    Text(text = "+")
                }
            }

        }
    }
}

@Composable
fun QuotesFeed(quotesList: List<QuoteResource>) {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(quotesList) {
            Quote(it)
        }
    }
}

@Composable
fun Quote(quoteResource: QuoteResource) {
    Column(
        modifier = Modifier
            .width(LocalConfiguration.current.screenWidthDp.dp)
            .padding(15.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(text = quoteResource.quote)
        Text(text = quoteResource.author)
        Spacer(modifier = Modifier.size(15.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = quoteResource.hits.toString(), fontSize = 20.sp, color = Color.Red)
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = {},
                modifier = Modifier.size(60.dp),
                shape = CircleShape,
                border = BorderStroke(5.dp, Color.Blue),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_star_border_24),
                    contentDescription = "Hit icon"
                )
            }
        }
    }
}