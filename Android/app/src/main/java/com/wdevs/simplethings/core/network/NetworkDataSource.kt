package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.datastore.RemoteDataStore
import com.wdevs.simplethings.core.model.QuotesResource
import java.util.concurrent.Flow

class NetworkDataSource(
    private val remoteDataStore : RemoteDataStore
) {
}