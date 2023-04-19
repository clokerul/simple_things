package com.wdevs.simplethings.feature.mylist

import androidx.lifecycle.ViewModel
import com.wdevs.simplethings.core.datastore.LocalDataSource
import com.wdevs.simplethings.core.network.NetworkDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(val localDataSource: LocalDataSource, val networkDataSource: NetworkDataSource) : ViewModel() {

}