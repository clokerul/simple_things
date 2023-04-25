package com.wdevs.simplethings.core.datastore.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wdevs.simplethings.core.model.QuoteResource

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getLocalQuotes()  : List<QuoteResource>

    @Query("SELECT * FROM quotes WHERE isRegret = 0")
    fun getLocalPathways() : List<QuoteResource>

    @Query("SELECT * FROM quotes WHERE isRegret = 1")
    fun getLocalRegrets() : List<QuoteResource>

    @Insert
    fun insertQuotes(vararg quoteResource: QuoteResource)

    @Delete
    fun deleteQuote(quoteResource: QuoteResource)
}