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
    TheListScreenStateless(uiState = theListUiState, theListViewModel::saveQuoteLocally)
}

@Composable
fun TheListScreenStateless(uiState: TheListUiState, onSaveQuoteLocally: (QuoteResource) -> Unit) {
    when (uiState) {
        TheListUiState.Loading -> Text("Loading")
        is TheListUiState.Success -> {
            QuotesFeed(uiState.quotesList, onSaveQuoteLocally)
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Button(modifier = Modifier.align(Alignment.BottomEnd), onClick = {}) {
//                    Text(text = "+")
//                }
//            }

        }
    }
}

@Composable
fun QuotesFeed(quotesList: List<QuoteResource>, onSaveQuoteLocally: (QuoteResource) -> Unit) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var offsetX by remember { mutableStateOf(0f) }
    var size by remember { mutableStateOf(Size.Zero) }
    var currentCardIndex by remember { mutableStateOf(0) }
    var scrollNow by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .fillMaxSize()
            .onSizeChanged { size = it.toSize() }) {
        LazyRow(
            state = listState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxHeight()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            offsetX = 0f
                            scrollNow = false
                        },
                        onDragEnd = {
                            val index = if (offsetX > 0) 0 else 1
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
                            coroutineScope.launch {
                                listState.animateScrollToItem(itemIndex)
                            }

                        }
                    ) { _, dragAmount ->
                        offsetX += dragAmount.x
                        if (!scrollNow && (abs(dragAmount.x) > 70 || abs(offsetX) > size.width / 2))
                            scrollNow = true
                        coroutineScope.launch {
                            listState.scrollBy(-dragAmount.x)

                        }
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(quotesList) {
                QuoteCard(it, onSaveQuoteLocally, coroutineScope)
            }
        }
    }
}

@Composable
fun QuoteCard(
    quoteResource: QuoteResource,
    onSaveQuoteLocally: (QuoteResource) -> Unit,
    coroutineScope: CoroutineScope,
) {
    val cardColor = if (quoteResource.isRegret) Color(247, 84, 155)
    else Color(89, 240, 197)
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var offsetY by remember { mutableStateOf(0f) }
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        modifier = Modifier
            .width(screenWidth.dp)
            .offset {
                IntOffset(0, offsetY.roundToInt())
            }
            .scale(if (offsetY > 500) 500 / offsetY else 1f)
            .padding(horizontal = 15.dp)
            .draggable(orientation = Orientation.Vertical,
                state = rememberDraggableState { dragAmount ->
                    offsetY = (offsetY + dragAmount).coerceIn(0f, 1000f)
                    coroutineScope.launch {
                        if (offsetY > 900) {
                            Toast
                                .makeText(context, "Saved to local", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                onDragStarted = {

                },
                onDragStopped = {
                    coroutineScope.launch {
                        var velocity = 10
                        while (abs(offsetY) > velocity * 2) {
                            Log.d("aaa", "QuotesFeed: iteration $offsetY")
                            offsetY -= if (offsetY > 0) velocity else -velocity;
                            velocity += 5
                            delay(10)
                        }
                        offsetY = 0f;
                    }
                }),
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
                    Text(
                        text = quoteResource.hits.toString(),
                        fontSize = 20.sp,
                        color = Color.Red
                    )
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

    //QuoteCard(quoteList[1], )
}