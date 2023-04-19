package com.wdevs.simplethings.feature.thelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuotesResource
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Destination
@Composable
fun TheListScreen(navController: NavController, theListViewModel: TheListViewModel = hiltViewModel()) {
    val theListUiState by theListViewModel.uiState.collectAsState()

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(theListUiState.quotes) { quote ->
            Quote(quote = quote)
        }
    }
}

@Composable
fun Quote(quote: QuotesResource) {
    Box() {
        Card(modifier = Modifier.height(100.dp)) {
            Text(text = quote.quote)
            Text(text = quote.author)
        }
    }
}