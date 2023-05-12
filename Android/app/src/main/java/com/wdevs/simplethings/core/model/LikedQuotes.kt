package com.wdevs.simplethings.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "liked_quotes")
data class LikedQuotes(
    @PrimaryKey val quoteId: Int = 0,
)
