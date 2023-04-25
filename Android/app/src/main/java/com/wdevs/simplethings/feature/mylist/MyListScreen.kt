package com.wdevs.simplethings.feature.mylist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuoteResource
import com.wdevs.simplethings.feature.QuotesFeed
import com.wdevs.simplethings.feature.thelist.TheListUiState
import com.wdevs.simplethings.feature.thelist.TheListViewModel


@Destination
@Composable
fun MyListScreen(
    navController: NavController,
    myListViewModel: MyListViewModel = hiltViewModel()
) {
    val myListUiState by myListViewModel.uiState.collectAsStateWithLifecycle()
    MyListScreenStateless(
        uiState = myListUiState,
        onSaveQuoteLocally = {}
    )
}

@Composable
fun MyListScreenStateless(uiState: MyListUiState, onSaveQuoteLocally: (QuoteResource) -> Unit) {
    when (uiState) {
        MyListUiState.Loading -> Text("Loading")
        is MyListUiState.Success -> {
            QuotesFeed(uiState.quotesList, onSaveQuoteLocally)
        }
    }
}