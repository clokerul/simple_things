package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class NetworkDataSource(
    private val quotesApi: QuotesApi
) {
    private val refreshIntervalMs: Long = 5 * 1000

    suspend fun getQuotes(): List<QuoteResource> = withContext(Dispatchers.IO) {
        quotesApi.getQuotes()
    }

    suspend fun postQuote(quote: QuoteResource) = withContext(Dispatchers.IO) {
        quotesApi.postQuote(quote)
    }


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
                        hits = 5125
                    ),
                    QuoteResource(
                        quote = "Once you are going to die, so live!",
                        isRegret = true,
                        hits = 45110
                    ),
                    QuoteResource(
                        quote = "Your true friends are few, make sure you will stick by them when " +
                                "hard times come!",
                        author = "Marian Dobre",
                        hits = 1234
                    ),
                    QuoteResource(
                        quote = "The real lover is a man who can thrill you by kissing your forehead or smiling into your eyes or just staring into space.",
                        hits = 73
                    ),
                    QuoteResource(
                        quote = "Don't submit to a boss before submitting to you husband, family is at all times yours.",
                        author = "Miruna Gealtu",
                        isRegret = true,
                        hits = 16219
                    )
                )
            } finally {
                emit(quotesList)
                delay(refreshIntervalMs)
            }
        }
    }
}