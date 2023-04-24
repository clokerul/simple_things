package com.wdevs.simplethings.core.datastore

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.model.QuoteResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val appContext: Application) {

    private val sharedPrefs: SharedPreferences =
        appContext.getSharedPreferences("preference_file", Context.MODE_PRIVATE)

    fun saveQuoteLocally(quoteResource: QuoteResource) {

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