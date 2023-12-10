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

import com.example.brocoli.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    onSubmitted: () -> Unit = {}
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    when (items) {
        is RegistrationUiState.Done -> {
            onSubmitted()
            return
        }

        is RegistrationUiState.Default -> {
            RegistrationScreen(
                onSave = viewModel::addRegistration,
                modifier = modifier
            )
        }

        is RegistrationUiState.Error -> {
            Text(text = (items as RegistrationUiState.Error).throwable.message!!)
        }
    }
}

@Composable
internal fun RegistrationScreen(
    onSave: (name: String, email: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        Modifier
            .fillMaxSize()
            .then(modifier), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
        var nameRegistration by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var confirmedEmail by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = nameRegistration,
            placeholder = { Text("Name") },
            onValueChange = { nameRegistration = it }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            placeholder = { Text("Email") },
            onValueChange = { email = it }
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmedEmail,
            placeholder = { Text("Confirm Email") },
            onValueChange = { confirmedEmail = it },
            isError = confirmedEmail != email,
        )

        Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameRegistration, email) }) {
            Text("Save")
        }
        Spacer(modifier = Modifier.size(12.dp))
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        RegistrationScreen(modifier = Modifier.padding(16.dp),onSave = { _, _ -> })
    }
}

