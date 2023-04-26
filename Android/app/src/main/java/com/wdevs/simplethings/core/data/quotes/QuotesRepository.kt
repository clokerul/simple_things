package com.wdevs.simplethings.core.data.quotes

import com.wdevs.simplethings.core.model.QuoteResource

interface QuotesRepository {
    suspend fun postQuote(quoteResource: QuoteResource)
    suspend fun saveQuoteLocal(quoteResource: QuoteResource)
}