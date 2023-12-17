package com.example.brocoli.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brocoli.data.RegistrationRepository
import com.example.brocoli.data.local.database.Registration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository
): ViewModel() {

    val uiState: StateFlow<HomeUiState> = registrationRepository
        .registrations.map<List<Registration>, HomeUiState>(HomeUiState::Success)
        .catch { emit(HomeUiState.Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState.Loading)

    fun onCancelAllClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            registrationRepository.deleteAll()
        }
    }

}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val throwable: Throwable) : HomeUiState
    data class Success(val data: List<Registration>) : HomeUiState
}