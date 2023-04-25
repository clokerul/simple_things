package com.wdevs.simplethings.feature.thelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.network.quotes.QuotesRepository
import com.wdevs.simplethings.core.network.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.model.QuoteResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TheListUiState {
    object Loading : TheListUiState
    data class Success(val quotesList: List<QuoteResource>) : TheListUiState
}

@HiltViewModel
class TheListViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    val uiState: StateFlow<TheListUiState> =
        (quotesRepository as QuotesRepositoryImpl).remoteQuotesStream.map { quotes ->
            TheListUiState.Success(quotes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = TheListUiState.Loading
        )

    fun postQuote(quoteResource: QuoteResource) {
        viewModelScope.launch {
            quotesRepository.postQuote(quoteResource)
        }
    }

    fun saveQuoteLocally(quoteResource: QuoteResource) {
        viewModelScope.launch {
            quotesRepository.saveQuoteLocally(quoteResource)
        }
    }
}