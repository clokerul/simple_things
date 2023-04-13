package com.wdevs.simplethings.core.data.quotes

import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.network.NetworkDataSource

// offline first
class OFQuotesRepository(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource) {
}