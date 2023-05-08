package com.wdevs.simplethings.core.datastore

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.datastore.database.AppDatabase
import com.wdevs.simplethings.core.datastore.database.QuoteDao
import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val appContext: Application, appDatabase: AppDatabase
) {
    companion object {
        private const val TAG = "LocalDataSource"
    }

    private val quoteDao: QuoteDao = appDatabase.quoteDao()
    private val sharedPrefs: SharedPreferences =
        appContext.getSharedPreferences("preference_file", Context.MODE_PRIVATE)

    /* --------------------------------------- FLOWS --------------------------------------- */
    val quotesStreamFlow: Flow<List<QuoteResource>> = flow {
        val localQuotes = withContext(Dispatchers.IO) {
            quoteDao.getLocalQuotes()
        }
        emit(localQuotes)
        Log.d(TAG, ": emittedNewQuotes")
        delay(1000)
    }

    /* --------------------------------------- GET ----------------------------------------- */
    fun getUsername(): String? {
        return sharedPrefs.getString(
            appContext.getString(R.string.username),
            appContext.getString(R.string.default_username)
        )
    }

    /* --------------------------------------- SAVE ---------------------------------------- */
    suspend fun saveQuote(quoteResource: QuoteResource) {
        withContext(Dispatchers.IO) {
            quoteDao.insertQuotes(quoteResource)
            Log.d(TAG, "saveQuoteLocally: $quoteResource")
        }
    }
    suspend fun saveUsername(username: String) = withContext(Dispatchers.IO) {
        with(sharedPrefs.edit()) {
            putString(appContext.getString(R.string.username), username)
            commit()
        }
    }

    /* --------------------------------------- UPDATE --------------------------------------- */

    suspend fun updateQuote(quoteResource: QuoteResource) {
        withContext(Dispatchers.IO) {
            quoteDao.updateLocalQuote(quoteResource)
            Log.d(TAG, "updateLocalQuote: $quoteResource")
        }
    }

}