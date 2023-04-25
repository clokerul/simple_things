package com.wdevs.simplethings.core.network.quotes

import android.util.Log
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.model.QuoteResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.log

// offline first
class QuotesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : QuotesRepository {

    val remoteQuotesStream: Flow<List<QuoteResource>> = networkDataSource.quotesStreamFlow
    val localQuotesStream: Flow<List<QuoteResource>> = localDataSource.quotesStreamFlow

    override suspend fun postQuote(quoteResource: QuoteResource) {
        networkDataSource.postQuote(quoteResource)
    }

    override suspend fun saveQuoteLocally(quoteResource: QuoteResource) {
        val localQuotes = localDataSource.getLocalQuotes()

        for (localQuote in localQuotes) {
            if (localQuote.id == quoteResource.id)
                return;
        }
        localDataSource.saveQuoteLocally(quoteResource)
    }
}