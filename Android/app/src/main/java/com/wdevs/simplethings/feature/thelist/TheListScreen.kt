package com.wdevs.simplethings.feature.thelist

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.model.QuoteResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wdevs.simplethings.R
import com.wdevs.simplethings.feature.QuotesFeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Destination
@Composable
fun TheListScreen(
    navController: NavController,
    theListViewModel: TheListViewModel = hiltViewModel()
) {
    val theListUiState by theListViewModel.uiState.collectAsStateWithLifecycle()
    TheListScreenStateless(
        uiState = theListUiState,
        onSaveQuoteLocally = theListViewModel::saveQuoteLocally
    )
}

@Composable
fun TheListScreenStateless(uiState: TheListUiState, onSaveQuoteLocally: (QuoteResource) -> Unit) {
    when (uiState) {
        TheListUiState.Loading -> Text("Loading")
        is TheListUiState.Success -> {
            QuotesFeed(uiState.quotesList, onSaveQuoteLocally)
        }
    }
}

@Composable
fun TheListScreenPreview() {

    //QuoteCard(quoteList[1], )
}