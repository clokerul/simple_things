package com.wdevs.simplethings.feature.thelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.quotes.QuotesRepositoryImpl
import com.wdevs.simplethings.core.model.QuotesResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheListViewModel @Inject constructor(
    private val quotesRepositoryImpl: QuotesRepositoryImpl
) : ViewModel() {
    private val _uiState = MutableStateFlow(TheListUiState())
    val uiState: StateFlow<TheListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            quotesRepositoryImpl.remoteQuotesStream
                .collect { remoteQuotes ->
                    _uiState.value = TheListUiState(quotes = remoteQuotes)
            }
        }
    }
}