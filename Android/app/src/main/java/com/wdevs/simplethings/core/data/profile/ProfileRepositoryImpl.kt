package com.wdevs.simplethings.core.data.profile

import android.app.Application
import android.content.Context
import com.wdevs.simplethings.R
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.network.NetworkDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource) : ProfileRepository{

    override suspend fun changeUsername(username: String) {
        localDataSource.changeUsername(username)
    }

    override fun getUsername() : String? {
        return localDataSource.getUsername();
    }
 }