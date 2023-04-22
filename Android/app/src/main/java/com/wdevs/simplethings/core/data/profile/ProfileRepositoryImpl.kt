package com.wdevs.simplethings.core.data.profile

import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.network.NetworkDataSource
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource, private val networkDataSource: NetworkDataSource) : ProfileRepository{
    override fun changeUsername(username: String) {
        localDataSource.changeUsername(username)
        networkDataSource.changeUsername(username)
    }
}