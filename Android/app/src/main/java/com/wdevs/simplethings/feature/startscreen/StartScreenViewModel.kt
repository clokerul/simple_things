package com.wdevs.simplethings.feature.startscreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.profile.ProfileRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface StartScreenUiState {
    data class Success(val username: String?) : StartScreenUiState
}

@HiltViewModel
class StartScreenViewModel @Inject constructor(private val profileRepositoryImpl: ProfileRepositoryImpl) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(StartScreenUiState.Success(profileRepositoryImpl.getUsername()))
    val uiState: StateFlow<StartScreenUiState> = _uiState

    fun onUsernameChange(username: String) {
        viewModelScope.launch {
            Log.d("ssvm", "onUsernameChange: ")
            _uiState.value = _uiState.value.copy(username = username)
            profileRepositoryImpl.changeUsername(username)
        }
    }
}