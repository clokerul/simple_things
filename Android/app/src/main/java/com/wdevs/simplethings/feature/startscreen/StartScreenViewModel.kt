package com.wdevs.simplethings.feature.startscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.profile.ProfileRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface StartScreenUiState {
    data class Success(val username: String?) : StartScreenUiState
}

@HiltViewModel
class StartScreenViewModel @Inject constructor(private val profileRepositoryImpl: ProfileRepositoryImpl) :
    ViewModel() {
    companion object {
        private const val TAG = "StartScreenViewModel"
    }

    private val _uiState =
        MutableStateFlow(StartScreenUiState.Success(profileRepositoryImpl.getUsername()))
    val uiState: StateFlow<StartScreenUiState> = _uiState

    fun saveUsername(username: String) {
        viewModelScope.launch {
            Log.d(TAG, "onUsernameChange: new username [$username]")
            _uiState.value = _uiState.value.copy(username = username)
            profileRepositoryImpl.saveUsername(username)
        }
    }
}