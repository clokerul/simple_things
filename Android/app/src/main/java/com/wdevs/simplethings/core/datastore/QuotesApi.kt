package com.wdevs.simplethings.core.datastore

import com.wdevs.simplethings.core.model.QuoteResource
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface QuotesApi {
    @GET("/quotes/pathways")
    suspend fun getPathways() : List<QuoteResource>

    @GET("/quotes/regrets")
    suspend fun getRegrets() : List<QuoteResource>

    @GET("/quotes")
    suspend fun getQuotes() : List<QuoteResource>

    @POST("/quotes/add")
    suspend fun postQuote(@Body quote: QuoteResource)
}