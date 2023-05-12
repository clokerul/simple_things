package com.wdevs.simplethings.feature.thelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.quotes.QuotesRepository
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.model.QuoteResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LikeableQuoteResource(
    val quoteResource: QuoteResource,
    val isLiked : Boolean
)

sealed interface TheListUiState {
    object Loading : TheListUiState
    data class Success(val quotesList: List<QuoteResource>) : TheListUiState
}

@HiltViewModel
class TheListViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    companion object {
        private const val TAG = "TheListViewModel"
    }

    val uiState: StateFlow<TheListUiState> =
        (quotesRepository as QuotesRepositoryImpl).remoteQuotesStream.map { quotes ->
            TheListUiState.Success(quotes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = TheListUiState.Loading
        )

    fun onQuoteLike(quoteResource: QuoteResource) {
        viewModelScope.launch {
            val hitSign: Int = 1

            // to be changed to get post method
            quotesRepository.updateQuote(
                quoteResource.copy(
                    hits = quoteResource.hits + hitSign * 1,
                )
            )
        }
    }

    fun postQuote(quoteResource: QuoteResource) {
        viewModelScope.launch {
            quotesRepository.postQuote(quoteResource)
        }
    }

    fun onQuoteDraggedToBottom(quoteResource: QuoteResource) {
        viewModelScope.launch {
            Log.d(TAG, "onQuoteDraggedToBottom: save quote")
            quotesRepository.saveQuoteLocal(quoteResource)
        }
    }
}