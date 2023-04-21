package com.wdevs.simplethings.core.model

data class QuoteResource(
    val author: String = "unknown",
    val hits: Int = 0,
    val id: Int = 0,
    val isRegret: Boolean = false,
    val quote: String,
)
