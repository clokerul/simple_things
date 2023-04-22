package com.wdevs.simplethings.core.data.profile

import com.wdevs.simplethings.core.datastore.LocalDataSource

interface ProfileRepository {
    fun changeUsername(username: String)
}