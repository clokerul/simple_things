package com.wdevs.simplethings.core.datastore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wdevs.simplethings.core.model.LikedQuotes
import com.wdevs.simplethings.core.model.QuoteResource

@Database(entities = [QuoteResource::class, LikedQuotes::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}