package com.wdevs.simplethings.feature

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

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
                    offsetY = (offsetY + dragAmount).coerceIn(-900f, 1000f)
                    coroutineScope.launch {
                        if (offsetY > 900) {
                            Toast
                                .makeText(context, "Saved to local", Toast.LENGTH_SHORT)
                                .show()
                            onSaveQuoteLocally(quoteResource)
                        }
                    }
                },
                onDragStarted = {
                },
                onDragStopped = {
                    coroutineScope.launch {
                        // Responsible to bring back the card
                        var velocity = 10
                        while (abs(offsetY) > velocity * 2) {
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