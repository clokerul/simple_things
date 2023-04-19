package com.wdevs.simplethings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wdevs.simplethings.core.data.quotes.OFQuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.di.ApiModule
import com.wdevs.simplethings.core.network.NetworkDataSource
import com.wdevs.simplethings.ui.Navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }
}
