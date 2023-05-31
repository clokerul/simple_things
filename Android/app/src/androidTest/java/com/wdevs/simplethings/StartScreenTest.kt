package com.wdevs.simplethings

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.wdevs.simplethings.core.model.QuoteResource
import com.wdevs.simplethings.feature.startscreen.StartScreenStateless
import com.wdevs.simplethings.feature.startscreen.StartScreenUiState
import com.wdevs.simplethings.feature.thelist.TheListScreenStateless
import com.wdevs.simplethings.feature.thelist.TheListUiState
import org.junit.Rule
import org.junit.Test

class StartScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun checkMenu() {
        rule.setContent { StartScreenStateless(
            uiState = StartScreenUiState.Success("Test User"),
            navigatoToDestination = { },
            saveUsernameEvent = { }
        ) }

        rule.onNodeWithText("Test User").assertExists()
        rule.onNodeWithText("THE LIST").assertExists()
        rule.onNodeWithText("MY LIST").assertExists()
        rule.onNodeWithText("whoami").assertExists()
    }

    @Test
    fun checkTheListScreen() {
        val quoteList : List<QuoteResource> = generateQuoteResourceList()

        rule.setContent { TheListScreenStateless(
            uiState = TheListUiState.Success(quoteList),
            onQuoteDraggedToBottom = { },
            onLikeButtonHit = { }
        ) }

        rule.onNodeWithText("Looking back will bring misery and regret, but looking forward.. " +
                "you are going to find hope and greatness.").assertExists()
    }

    @Test
    fun checkTheListScreenLoading() {
        rule.setContent { TheListScreenStateless(
            uiState = TheListUiState.Loading,
            onQuoteDraggedToBottom = {},
            onLikeButtonHit = {},
        ) }

        rule.onNodeWithText("Loading").assertExists()
    }

    fun generateQuoteResourceList() : List<QuoteResource> {
        return listOf(
            QuoteResource(
                quote = "Looking back will bring misery and regret, but looking forward.. " +
                        "you are going to find hope and greatness.",
                hits = 5125,
                id = 1
            ),
            QuoteResource(
                quote = "Once you are going to die, so live!",
                isRegret = true,
                hits = 45110,
                id = 2
            ),
            QuoteResource(
                quote = "Your true friends are few, make sure you will stick by them when " +
                        "hard times come!",
                author = "Marian Dobre",
                hits = 1234,
                id = 3
            ),
            QuoteResource(
                quote = "The real lover is a man who can thrill you by kissing your forehead or smiling into your eyes or just staring into space.",
                hits = 73,
                id = 4
            ),
            QuoteResource(
                quote = "Don't submit to a boss before submitting to your lover, family is at all times yours and it should come first.",
                author = "Miruna Gealtu",
                isRegret = true,
                hits = 16219,
                id = 5,
            )
        )
    }
}