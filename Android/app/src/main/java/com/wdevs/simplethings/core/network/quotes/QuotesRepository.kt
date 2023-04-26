package com.wdevs.simplethings.core.network.quotes

import com.wdevs.simplethings.core.model.QuoteResource

interface QuotesRepository {
    suspend fun postQuote(quoteResource: QuoteResource)
    suspend fun saveQuoteLocally(quoteResource: QuoteResource)
}