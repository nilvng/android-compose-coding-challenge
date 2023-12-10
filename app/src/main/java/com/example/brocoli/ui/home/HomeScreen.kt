package com.example.brocoli.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.brocoli.data.local.database.Registration
import com.example.brocoli.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(modifier: Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    when (items) {
        is HomeUiState.Success -> {
            HomeScreen(
                modifier = modifier,
                registration = (items as HomeUiState.Success).data
            )
        }

        else -> {
            Text(text = "Loading...")
        }
    }
}

@Composable
internal fun HomeScreen(modifier: Modifier, registration: List<Registration>) {
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

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Cancel my registration")
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