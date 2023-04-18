package com.wdevs.simplethings.core.model

data class QuotesResource(
    val author: String,
    val hits: Int,
    val id: Int,
    val isRegret: Boolean,
    val quote: String,
)
