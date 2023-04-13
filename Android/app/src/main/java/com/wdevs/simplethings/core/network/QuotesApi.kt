package com.wdevs.simplethings.core.network

import com.wdevs.simplethings.core.model.QuotesResource
import retrofit2.Response
import retrofit2.http.GET

interface QuotesApi {
    @GET("/pathways")
    suspend fun getPathways() : Response<QuotesResource>
}