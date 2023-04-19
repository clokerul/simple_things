package com.wdevs.simplethings.core.data.quotes

import com.wdevs.simplethings.core.model.QuotesResource

// todo implement repository functions
interface QuotesRepository {

    val remoteFetchTimeoutMs : Long
        get() = 1000L

    suspend fun getRemoteQuotes() : List<QuotesResource>
    fun getLocalQuotes()
}