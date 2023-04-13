package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.model.QuotesResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(
    private val quotesApi: QuotesApi
) {
    val quotesStream: Flow<List<QuotesResource>> = flow {
        while (true) {
            quotesApi.getPathways()
        }
    }

}