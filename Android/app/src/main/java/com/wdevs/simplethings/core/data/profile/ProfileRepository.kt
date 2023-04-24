package com.wdevs.simplethings.core.data.profile

import com.wdevs.simplethings.core.datastore.LocalDataSource

interface ProfileRepository {
    suspend fun changeUsername(username: String)
    fun getUsername(): String?
}