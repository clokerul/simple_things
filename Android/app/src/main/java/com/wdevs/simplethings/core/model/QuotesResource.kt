package com.wdevs.simplethings.core.model

data class QuotesResource(
    val lastItemIndex: String,
    val hits: Int,
    val id: Int,
    val isRegret: Boolean,
    val quote: String,
)
