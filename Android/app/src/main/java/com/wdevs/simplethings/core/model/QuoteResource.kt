package com.wdevs.simplethings.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteResource(
    @PrimaryKey val id: Int = 0,
    val author: String = "unknown",
    val hits: Int = 0,
    val isRegret: Boolean = false,
    val quote: String,
)
