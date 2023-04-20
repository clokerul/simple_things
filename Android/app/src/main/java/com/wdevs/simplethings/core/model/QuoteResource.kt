package com.wdevs.simplethings.core.model

data class QuoteResource(
    val author: String,
    val hits: Int,
    val id: Int,
    val isRegret: Boolean,
    val quote: String,
)
