package com.wdevs.simplethings.core.data.quotes

import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.model.QuoteResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// offline first
class QuotesRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : QuotesRepository {

    val remoteQuotesStream : Flow<List<QuoteResource>> = networkDataSource.quotesStreamFlow
    override suspend fun postQuote(quote: QuoteResource) {
        networkDataSource.postQuote(quote)
    }
}