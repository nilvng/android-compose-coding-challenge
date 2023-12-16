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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: State<RegistrationUiState>,
    isLoadingState: State<Boolean>,
    onSubmitted: () -> Unit = {},
    onSendingRequest: (String, String) -> Unit = { _, _ -> },
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState.value) {
            is RegistrationUiState.Done -> {
                onSubmitted()
                return
            }

            is RegistrationUiState.Default -> {
                Text(
                    text = "Please fill the form below",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            is RegistrationUiState.Error -> {
                Text(
                    text = "Error: ${(uiState.value as RegistrationUiState.Error).throwable.message!!}",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        RegistrationFormView(
            onSave = onSendingRequest,
            isLoading = isLoadingState,
        )
    }
}

@Composable
internal fun RegistrationFormView(
    onSave: (name: String, email: String) -> Unit,
    isLoading: State<Boolean>,
) {

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
    Spacer(modifier = Modifier.size(4.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading.value) {
            CircularProgressIndicator()
        }
        Button(
            modifier = Modifier.width(96.dp),
            onClick = { onSave(nameRegistration, email) }) {
            Text("Save")
        }
    }
    Spacer(modifier = Modifier.size(12.dp))
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        RegistrationScreen(modifier = Modifier.padding(16.dp),
            uiState = remember {
                mutableStateOf(RegistrationUiState.Default)
            },
            isLoadingState = remember {
                mutableStateOf(false)
            })
    }
}

@Preview(showBackground = true)
@Composable
private fun RequestLoadingPreview() {
    MyApplicationTheme {
        RegistrationScreen(modifier = Modifier.padding(16.dp),
            uiState = remember {
                mutableStateOf(RegistrationUiState.Error(Throwable("We couldn't register you with email")))
            },
            isLoadingState = remember {
                mutableStateOf(false)
            })
    }
}

