/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.brocoli.ui.registration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.brocoli.data.RegistrationRepository
import com.example.brocoli.data.remote.BroccoliApiService
import com.example.brocoli.data.remote.RegistrationBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository
) : ViewModel() {

    val uiState: StateFlow<RegistrationUiState> get() = _uiState.asStateFlow()
    private val _uiState: MutableStateFlow<RegistrationUiState> =
        MutableStateFlow(RegistrationUiState.Default)

    val isLoadingState get() = _isLoadingState
    private val _isLoadingState: MutableState<Boolean> = mutableStateOf(false)

    fun addRegistration(name: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingState.value = true
            sendRequest(name, email)
            _isLoadingState.value = false
        }
    }

    private suspend fun sendRequest(name: String, email: String) {
        val request = BroccoliApiService.myRetrofit.register(
            RegistrationBody(
                name = name,
                email = email
            )
        ).execute()
        if (request.isSuccessful) {
            registrationRepository.add(name, email)
            _uiState.emit(RegistrationUiState.Done)
        } else {
            _uiState.emit(RegistrationUiState.Error(Throwable("Sorry, this email has been used")))
        }
    }
}

sealed interface RegistrationUiState {
    data object Default : RegistrationUiState
    data class Error(val throwable: Throwable) : RegistrationUiState
    data object Done : RegistrationUiState
}
