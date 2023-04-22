package com.wdevs.simplethings.feature.startscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wdevs.simplethings.core.data.profile.ProfileRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface StartScreenUiState {    data class Success(val username: String) : StartScreenUiState
}
@HiltViewModel
class StartScreenViewModel @Inject constructor(val profileRepositoryImpl: ProfileRepositoryImpl) : ViewModel() {
    val uiState: StateFlow<StartScreenUiState> = MutableStateFlow(StartScreenUiState.Success("The Black Castor"))

    fun onUsernameChange(username: String) {
        viewModelScope.launch {
            profileRepositoryImpl.changeUsername(username)
        }
    }
}