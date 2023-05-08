package com.wdevs.simplethings.feature.thelist

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuoteResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wdevs.simplethings.feature.QuotesFeed

@Destination
@Composable
fun TheListScreen(
    navController: NavController,
    theListViewModel: TheListViewModel = hiltViewModel()
) {
    val theListUiState by theListViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    TheListScreenStateless(
        uiState = theListUiState,
        onQuoteDraggedToBottom = { quoteResource ->
            theListViewModel.onQuoteDraggedToBottom(quoteResource)
            Toast.makeText(context, "Quote saved to your list!", Toast.LENGTH_SHORT).show()
        },
        onLikeButtonHit = { quoteResource ->
            theListViewModel.onQuoteLike(quoteResource)
        }
    )
}

@Composable
fun TheListScreenStateless(
    uiState: TheListUiState,
    onQuoteDraggedToBottom: (QuoteResource) -> Unit,
    onLikeButtonHit: (QuoteResource) -> Unit
) {
    when (uiState) {
        TheListUiState.Loading -> CenterText("Loading")
        is TheListUiState.Success -> {
            QuotesFeed(uiState.quotesList, onQuoteDraggedToBottom, onLikeButtonHit, false)
        }
    }
}

@Composable
fun CenterText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text)
    }
}

@Composable
fun TheListScreenPreview() {

    //QuoteCard(quoteList[1], )
}