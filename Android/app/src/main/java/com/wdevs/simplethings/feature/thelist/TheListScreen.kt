package com.wdevs.simplethings.feature.thelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.wdevs.simplethings.core.data.quotes.OFQuotesRepository
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.datastore.QuotesApi
import com.wdevs.simplethings.core.di.ApiModule
import com.wdevs.simplethings.core.model.QuotesResource
import com.wdevs.simplethings.core.network.NetworkDataSource
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Destination
@Composable
fun TheListScreen(navController: NavController) {
    val quotesApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(ApiModule.BASE_URL)
        .build()
        .create(QuotesApi::class.java)
    val ofQuotesRepository: OFQuotesRepository =
        OFQuotesRepository(LocalDataSource(), NetworkDataSource(quotesApi = quotesApi))
    val remoteQuotes: List<QuotesResource>

    runBlocking {
        remoteQuotes = ofQuotesRepository.getRemoteQuotes()
    }

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(remoteQuotes) { quote ->
            Quote(quote = quote)
        }
    }
}

@Composable
fun Quote(quote: QuotesResource) {
    Box() {
        Card(modifier = Modifier.height(100.dp)) {
            Text(text = quote.quote)
            Text(text = quote.author)
        }
    }
}