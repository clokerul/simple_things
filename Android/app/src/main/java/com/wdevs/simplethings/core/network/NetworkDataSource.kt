package com.wdevs.simplethings.core.network

import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.app.NotificationCompat
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class NetworkDataSource(
    private val quotesApi: QuotesApi
) {
    private val refreshIntervalMs: Long = 1 * 100
    val quotesStreamFlow: Flow<List<QuoteResource>> = flow {
        while (true) {
            var quotesList: List<QuoteResource> = emptyList()
            try {
                quotesList = getQuotes()
            } catch (_: Exception) {
                quotesList = listOf(
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
            } finally {
                emit(quotesList)
                Log.d(TAG, "emmitted list: ")
                delay(refreshIntervalMs)
            }
        }
    }

    private suspend fun getQuotes(): List<QuoteResource> = withContext(Dispatchers.IO) {
        quotesApi.getQuotes()
    }

    suspend fun postQuote(quote: QuoteResource) = withContext(Dispatchers.IO) {
        quotesApi.postQuote(quote)
    }

    fun saveUsername(username: String) {
        TODO("Implement backend username connections")
    }

    suspend fun updateQuote(quoteResource: QuoteResource) {
        quotesApi.updateQuote(quoteResource)
    }
}