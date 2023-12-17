package com.example.brocoli.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brocoli.data.local.database.Registration
import com.example.brocoli.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(
    modifier: Modifier,
    uiState: State<HomeUiState>,
    onRegisteredAnotherOne: () -> Unit,
    onCancelAllClicked: () -> Unit,
) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box {
        when (uiState.value) {
            is HomeUiState.Success -> {
                HomeScreen(
                    modifier = modifier,
                    registration = (uiState.value as HomeUiState.Success).data,
                    onRegisteredAnotherOne = onRegisteredAnotherOne,
                    onCancelAllClicked = { showDialog = true }
                )
            }

            else -> {
                Text(text = "Loading...")
            }
        }
        if (showDialog) {
            AlertDialog(
                properties = DialogProperties(),
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Are you sure you want to cancel all of your registration?") },
                text = { Text(text = "This action cannot be undone.") },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }, colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Text(text = "no, my mistake")
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        onCancelAllClicked.invoke()
                    }) {
                        Text(text = "Yes")
                    }
                },
            )
        }
    }
}

@Composable
internal fun HomeScreen(
    modifier: Modifier, registration: List<Registration>,
    onRegisteredAnotherOne: () -> Unit = {},
    onCancelAllClicked: () -> Unit = {}
) {
    Box(Modifier.fillMaxSize()) {
        Column(modifier) {
            Text(text = "Congrats! You have successfully registered.")

            registration.forEach {
                Card {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = it.name)
                        Text(text = "[${it.email}]")
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onCancelAllClicked, colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(text = "Cancel all")
            }

            Button(onClick = onRegisteredAnotherOne) {
                Text(text = "Register another one")

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        HomeScreen(
            modifier = Modifier, registration = listOf(
                Registration(name = "name", email = "email")
            )
        )
    }
}