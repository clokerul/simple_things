package com.wdevs.simplethings.core.di

import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    val BASE_URL = "http://192.168.253.1:8000"

    @Provides
    @Singleton
    fun provideQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(QuotesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNetworkDataSource(api : QuotesApi) :NetworkDataSource {
        return NetworkDataSource(api)
    }
}