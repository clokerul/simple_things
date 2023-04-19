package com.wdevs.simplethings.feature.thelist

import com.wdevs.simplethings.core.model.QuotesResource

data class TheListUiState (
    val quotes: List<QuotesResource> = emptyList()
)