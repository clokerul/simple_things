package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.model.QuotesResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Arrays.asList
import javax.inject.Inject

class NetworkDataSource(
    private val quotesApi: QuotesApi
) {
    private val refreshIntervalMs : Long = 300 * 1000

    suspend fun getQuotes(): List<QuotesResource> = withContext(Dispatchers.IO) {
        quotesApi.getPathways()
    }


    val quotesStreamFlow: Flow<List<QuotesResource>> = flow {
        while (true) {
            val quotesList = getQuotes()
            emit(quotesList)
            delay(refreshIntervalMs)
        }
    }
}