package com.example.brocoli.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brocoli.data.RegistrationRepository
import com.example.brocoli.data.local.database.Registration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    registrationRepository: RegistrationRepository
): ViewModel() {
    val uiState: StateFlow<HomeUiState> = registrationRepository
        .registrations.map<List<Registration>, HomeUiState>(HomeUiState::Success)
        .catch { emit(HomeUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState.Loading)

}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val throwable: Throwable) : HomeUiState
    data class Success(val data: List<Registration>) : HomeUiState
}