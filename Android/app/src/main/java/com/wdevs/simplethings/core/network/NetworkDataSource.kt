package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class NetworkDataSource(
    private val quotesApi: QuotesApi
) {
    private val refreshIntervalMs : Long = 5 * 1000

    suspend fun getQuotes(): List<QuoteResource> = withContext(Dispatchers.IO) {
        quotesApi.getQuotes()
    }

    suspend fun postQuote(quote: QuoteResource) = withContext(Dispatchers.IO) {
        quotesApi.postQuote(quote)
    }


    val quotesStreamFlow: Flow<List<QuoteResource>> = flow {
        while (true) {
            val quotesList = getQuotes()
            emit(quotesList)
            delay(refreshIntervalMs)
        }
    }
}