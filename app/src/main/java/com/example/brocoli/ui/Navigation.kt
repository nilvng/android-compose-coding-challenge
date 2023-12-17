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

package com.example.brocoli.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brocoli.ui.home.HomeScreen
import com.example.brocoli.ui.home.HomeViewModel
import com.example.brocoli.ui.registration.RegistrationScreen
import com.example.brocoli.ui.registration.RegistrationUiState
import com.example.brocoli.ui.registration.RegistrationViewModel
import kotlinx.coroutines.flow.stateIn

@Composable
fun MainNavigation(top: Dp) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registeredList") {
        composable("main") {
            val registrationViewModel = hiltViewModel<RegistrationViewModel>()
            RegistrationScreen(
                modifier = Modifier.padding(12.dp, top),
                uiState = registrationViewModel.uiState.collectAsState(),
                isLoadingState = registrationViewModel.isLoadingState,
                onSubmitted = {
                    navController.navigate("registeredList")
                },
                onSendingRequest = { name, email ->
                    registrationViewModel.addRegistration(name, email)
                })
        }
        composable("registeredList") {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(modifier = Modifier.padding(12.dp, top),
                uiState = homeViewModel.uiState.collectAsState(),
                onRegisteredAnotherOne = {
                    navController.navigate("main")
                }, onCancelAllClicked = {
                    homeViewModel.onCancelAllClicked()
                })
        }
    }
}
