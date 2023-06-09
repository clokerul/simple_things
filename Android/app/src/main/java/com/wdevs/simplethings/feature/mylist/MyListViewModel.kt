package com.wdevs.simplethings.feature.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.network.quotes.QuotesRepository
import com.wdevs.simplethings.core.network.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.model.QuoteResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface MyListUiState {
    object Loading : MyListUiState
    data class Success(val myQuotesList: List<QuoteResource>) : MyListUiState
}
@HiltViewModel
class MyListViewModel @Inject constructor(private val quotesRepository: QuotesRepository) : ViewModel() {
    val uiState: StateFlow<MyListUiState> =
        (quotesRepository as QuotesRepositoryImpl).localQuotesStream.map { quotes ->
            MyListUiState.Success(quotes)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = MyListUiState.Loading
        )
}