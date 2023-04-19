package com.wdevs.simplethings.core.data.quotes

import android.util.Log
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.model.QuotesResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

// offline first
class QuotesRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource
) : QuotesRepository {

    val remoteQuotesStream : Flow<List<QuotesResource>> = networkDataSource.quotesStreamFlow
}