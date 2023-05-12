package com.wdevs.simplethings.feature.mylist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuoteResource
import com.wdevs.simplethings.feature.QuotesFeed


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
        MyListUiState.Loading -> CenterText("Importing from local storage..")
        is MyListUiState.Success -> {
            if (uiState.myQuotesList.isEmpty()) CenterText("You can add quotes from the global list")
            else QuotesFeed(uiState.myQuotesList, onSaveQuoteLocally, {}, true)
        }
    }
}

@Composable
fun CenterText(text : String) {
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text)
    }
}