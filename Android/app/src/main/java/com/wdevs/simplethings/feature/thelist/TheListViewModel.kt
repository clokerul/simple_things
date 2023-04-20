package com.wdevs.simplethings.feature.thelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
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
    private val quotesRepositoryImpl: QuotesRepositoryImpl
) : ViewModel() {
    val uiState: StateFlow<TheListUiState> =
        quotesRepositoryImpl.remoteQuotesStream.map { quotes ->
            TheListUiState.Success(quotes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = TheListUiState.Loading
        )

    fun postQuote(quote: QuoteResource) {
        viewModelScope.launch {
            quotesRepositoryImpl.postQuote(quote)
        }
    }
}