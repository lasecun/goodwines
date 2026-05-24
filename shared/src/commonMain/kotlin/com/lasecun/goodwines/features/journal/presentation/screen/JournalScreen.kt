package com.lasecun.goodwines.features.journal.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.presentation.components.GoodwinesCard
import com.lasecun.goodwines.core.presentation.components.GoodwinesErrorView
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalUiState
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalViewModel
import com.lasecun.goodwines.core.presentation.components.RatingStars
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    onEntryClick: (entryId: String) -> Unit = {},
    onAddEntry: () -> Unit = {},
    onScanWine: () -> Unit = {}
) {
    val viewModel: JournalViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Wine Journal") },
                actions = {
                    androidx.compose.material3.TextButton(onClick = onScanWine) {
                        Text("📷", style = MaterialTheme.typography.titleMedium)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEntry) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val s = state) {
                is JournalUiState.Loading -> GoodwinesLoadingIndicator()
                is JournalUiState.Error -> GoodwinesErrorView(
                    message = s.message,
                    onRetry = { viewModel.loadEntries() }
                )
                is JournalUiState.Success -> {
                    if (s.entries.isEmpty()) {
                        EmptyJournalView(onAddEntry = onAddEntry)
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
                        ) {
                            items(s.entries) { entry ->
                                JournalEntryCard(
                                    entry = entry,
                                    onClick = { onEntryClick(entry.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyJournalView(onAddEntry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🍷", style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.height(16.dp))
        Text(
            "Your journal is empty",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Start logging wines you taste and build your personal collection.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        com.lasecun.goodwines.core.presentation.components.GoodwinesButton(
            text = "Log your first wine",
            onClick = onAddEntry
        )
    }
}

@Composable
private fun JournalEntryCard(
    entry: TastingEntry,
    onClick: () -> Unit
) {
    GoodwinesCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = entry.wine.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = entry.wine.winery,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (entry.wine.vintage != null) {
                        Text(
                            text = entry.wine.vintage.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                RatingStars(rating = entry.rating, starSize = 16.dp)
            }
            if (entry.notes.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = entry.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}
