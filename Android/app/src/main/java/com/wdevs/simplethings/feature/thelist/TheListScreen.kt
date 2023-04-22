package com.wdevs.simplethings.feature.thelist

import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wdevs.simplethings.R
import kotlinx.coroutines.launch
import kotlin.math.abs

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
            ) {
                Button(modifier = Modifier.align(Alignment.BottomEnd), onClick = {}) {
                    Text(text = "+")
                }
            }

        }
    }
}

@Composable
fun QuotesFeed(quotesList: List<QuoteResource>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var offset = 0f
    var scrollNow = false
    var currentCardIndex: Int = 0

    LazyRow(
        state = listState,
        userScrollEnabled = false,
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                orientation = Orientation.Horizontal,
                // Scrollable state: consuming state changes
                state = rememberDraggableState { delta ->
                    offset += delta
                    if (!scrollNow && (abs(delta) > 70 || abs(offset) > listState.layoutInfo.viewportEndOffset / 2))
                        scrollNow = true
                    coroutineScope.launch {
                        listState.scrollBy(-delta)
                    }
                },
                onDragStarted = {
                    offset = 0f
                    scrollNow = false
                },
                onDragStopped = {
                    coroutineScope.launch {
                        val index = if (offset > 0) 0 else 1
                        var itemIndex = 0

                        // Decide which item should be scrolled to
                        itemIndex = if (!scrollNow) {
                            currentCardIndex
                        } else if (listState.layoutInfo.visibleItemsInfo.size <= 1) {
                            listState.layoutInfo.visibleItemsInfo[0].index
                        } else {
                            listState.layoutInfo.visibleItemsInfo[index].index
                        }

                        // Save the current item
                        currentCardIndex = itemIndex
                        listState.animateScrollToItem(itemIndex)
                    }
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(quotesList) {
            QuoteCard(it)
        }
    }
}


@Composable
fun QuoteCard(quoteResource: QuoteResource) {
    val cardColor = if (quoteResource.isRegret) Color(247, 84, 155)
    else Color(89, 240, 197)
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val state = rememberDraggableState(
        onDelta = { delta -> Log.d("QuoteCard", "Dragged $delta") }
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .width(screenWidth.dp)
            .padding(horizontal = 15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
    ) {
        Row {
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier.width((screenWidth - 70).dp),
                horizontalAlignment = Alignment.End,
            ) {
                Spacer(modifier = Modifier.size(20.dp))
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
                Spacer(modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun TheListScreenPreview() {
    val quoteList: List<QuoteResource> = listOf<QuoteResource>(
        QuoteResource(
            quote = "Once you are going to die, so live!",
            isRegret = true
        ),
        QuoteResource(
            quote = "Looking back forward will bring misery and regret, but looking forward " +
                    "you are going to find hope and greatness"
        )
    )
    QuoteCard(quoteList[1])
}