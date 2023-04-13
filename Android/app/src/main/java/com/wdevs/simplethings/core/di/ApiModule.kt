package com.wdevs.simplethings.core.di

import com.wdevs.simplethings.core.datastore.QuotesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    val BASE_URL = "https://localhost:8000"

    @Provides
    @Singleton
    fun provideQuotesApi(): QuotesApi {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(QuotesApi::class.java)
    }
}