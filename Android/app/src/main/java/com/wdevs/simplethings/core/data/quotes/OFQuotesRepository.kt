package com.wdevs.simplethings.core.data.quotes

import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.model.QuotesResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout

// offline first
class OFQuotesRepository(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : QuotesRepository {

    val remoteQuotesStream : Flow<List<QuotesResource>> = networkDataSource.quotesStreamFlow

    override suspend fun getRemoteQuotes() : List<QuotesResource>? {
        var quotesList : List<QuotesResource>? = null

        print("Getting quotes")
        withTimeout(remoteFetchTimeoutMs) {
            quotesList = networkDataSource.getQuotes()
        }
        print(quotesList)

        return quotesList
    }

    override fun getLocalQuotes() {
        TODO("Not yet implemented")
    }
}