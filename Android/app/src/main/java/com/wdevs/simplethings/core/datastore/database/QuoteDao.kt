package com.wdevs.simplethings.core.datastore.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wdevs.simplethings.core.model.QuoteResource

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quotes")
    fun getLocalQuotes()  : List<QuoteResource>

    @Query("SELECT * FROM quotes WHERE isRegret = 0")
    fun getLocalPathways() : List<QuoteResource>

    @Query("SELECT * FROM quotes WHERE isRegret = 1")
    fun getLocalRegrets() : List<QuoteResource>

    @Update
    fun updateLocalQuote(quote: QuoteResource)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuotes(vararg quoteResource: QuoteResource)

    @Delete
    fun deleteQuote(quoteResource: QuoteResource)
}