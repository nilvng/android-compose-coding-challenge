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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.brocoli.ui.home.HomeScreen
import com.example.brocoli.ui.registration.RegistrationScreen

@Composable
fun MainNavigation(top: Dp) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "registeredList") {
        composable("main") {
            RegistrationScreen(modifier = Modifier.padding(12.dp, top), onSubmitted = {
                navController.navigate("registeredList")
            })
        }
        composable("registeredList") { HomeScreen(modifier = Modifier.padding(12.dp, top)) }
    }
}
