package com.wdevs.simplethings.core.data.profile

import com.wdevs.simplethings.core.datastore.LocalDataSource

class LocalProfileRepository(private val localDataSource: LocalDataSource) : ProfileRepository{
}