package com.wdevs.simplethings.core.di

import android.app.Application
import androidx.room.Room
import com.wdevs.simplethings.core.data.profile.ProfileRepository
import com.wdevs.simplethings.core.data.profile.ProfileRepositoryImpl
import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.datastore.database.AppDatabase
import com.wdevs.simplethings.core.network.QuotesApi
import com.wdevs.simplethings.core.network.NetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataAccessModule {
    private const val BASE_URL = "http://192.168.1.128:8000"

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
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app-database").build()
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(api: QuotesApi): NetworkDataSource {
        return NetworkDataSource(api)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(app: Application, appDatabase: AppDatabase): LocalDataSource {
        return LocalDataSource(app, appDatabase)
    }

    @Provides
    @Singleton
    fun provideQuotesRepository(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource
    ): QuotesRepository {
        return QuotesRepositoryImpl(localDataSource, networkDataSource)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        localDataSource: LocalDataSource,
        networkDataSource: NetworkDataSource
    ): ProfileRepository {
        return ProfileRepositoryImpl(localDataSource, networkDataSource)
    }
}