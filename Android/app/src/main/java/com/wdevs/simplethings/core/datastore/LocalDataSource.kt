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
    private val appContext: Application, private val appDatabase: AppDatabase
) {
    private final val TAG = "LocalDataSource"
    private val sharedPrefs: SharedPreferences =
        appContext.getSharedPreferences("preference_file", Context.MODE_PRIVATE)
    private val quoteDao: QuoteDao = appDatabase.quoteDao()

    val quotesStreamFlow: Flow<List<QuoteResource>> = flow {
        val value = getLocalQuotes()
        delay(1000)
        emit(value)
        Log.d(TAG, ": emitedNewQuotes")
    }

    suspend fun getLocalQuotes(): List<QuoteResource> = withContext(Dispatchers.IO) {
        quoteDao.getLocalQuotes()
    }

    suspend fun saveQuoteLocally(quoteResource: QuoteResource) {
        withContext(Dispatchers.IO) {
            quoteDao.insertQuotes(quoteResource)
            Log.d(TAG, "saveQuoteLocally: ")
        }
    }

    suspend fun changeUsername(username: String) = withContext(Dispatchers.IO) {
        with(sharedPrefs.edit()) {
            putString(appContext.getString(R.string.username), username)
            commit()
        }
    }

    fun getUsername(): String? {
        return sharedPrefs.getString(appContext.getString(R.string.username), "The Black castor")
    }
}