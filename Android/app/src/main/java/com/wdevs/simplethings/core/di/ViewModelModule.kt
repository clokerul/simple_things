package com.wdevs.simplethings.core.di

import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
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
    fun provideNetworkDataSource(api : QuotesApi) :NetworkDataSource {
        return NetworkDataSource(api)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource() : LocalDataSource {
        return LocalDataSource()
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(localDataSource: LocalDataSource, networkDataSource: NetworkDataSource) : QuotesRepository {
        return QuotesRepositoryImpl(localDataSource, networkDataSource)
    }
}