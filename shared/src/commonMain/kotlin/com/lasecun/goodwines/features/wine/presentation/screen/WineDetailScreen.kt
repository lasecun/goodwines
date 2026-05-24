package com.lasecun.goodwines.features.wine.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.features.wine.presentation.viewmodel.WineDetailViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineDetailScreen(
    wineId: String,
    viewModel: WineDetailViewModel = koinInject(),
    onBack: () -> Unit = {},
    onNavigateToJournal: (wineId: String) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(wineId) {
        viewModel.loadWine(wineId)
    }

    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            viewModel.clearToast()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wine Details") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.wine == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Wine not found")
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🍷",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = state.wine?.name ?: "",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = state.wine?.winery ?: "",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Divider()

                    WineInfoRow("Vintage:", state.wine?.vintage?.toString() ?: "—")
                    WineInfoRow("ABV:", (state.wine?.abv?.let { "${it}%" } ?: "—"))
                    WineInfoRow("Region:", state.wine?.region ?: "—")
                    WineInfoRow("Grapes:", state.wine?.grapes?.joinToString(", ") ?: "—")
                    WineInfoRow("Rating:", state.wine?.averageRating?.let { "$it/5" } ?: "—")

                    Divider()

                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = state.wine?.description ?: "No description available",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Divider()

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { state.wine?.let { viewModel.addToWishlist() } },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Lo quiero probar")
                        }
                        Button(
                            onClick = { state.wine?.let { viewModel.markAsTasted() } },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Lo he probado")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WineInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}
