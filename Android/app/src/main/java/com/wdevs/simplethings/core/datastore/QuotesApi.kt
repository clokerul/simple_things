package com.wdevs.simplethings.core.datastore

import com.wdevs.simplethings.core.model.QuotesResource
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/pathways")
    suspend fun getPathways() : Response<QuotesResource>

    @GET("/regrets")
    suspend fun getRegrets() : Response<QuotesResource>

    @GET("/quotes")
    suspend fun getQuotes() : Response<QuotesResource>
}