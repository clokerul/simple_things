package com.wdevs.simplethings.core.data.quotes

import android.util.Log
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.model.QuotesResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout

// offline first
class OFQuotesRepository(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : QuotesRepository {

    val remoteQuotesStream : Flow<List<QuotesResource>> = networkDataSource.quotesStreamFlow
    val TAG = "OFQuotesRepository"

    override suspend fun getRemoteQuotes() : List<QuotesResource> {
        var quotesList : List<QuotesResource> = emptyList()

        Log.d(TAG, "no list")
        withTimeout(remoteFetchTimeoutMs) {
            quotesList = networkDataSource.getQuotes()
        }
        Log.d(TAG, quotesList.toString())

        return quotesList
    }

    override fun getLocalQuotes() {
        TODO("Not yet implemented")
    }
}