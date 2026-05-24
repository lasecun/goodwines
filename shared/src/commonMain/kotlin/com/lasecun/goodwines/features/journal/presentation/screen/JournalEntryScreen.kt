package com.lasecun.goodwines.features.journal.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
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
import com.lasecun.goodwines.core.presentation.components.GoodwinesButton
import com.lasecun.goodwines.core.presentation.components.GoodwinesErrorView
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.core.presentation.components.RatingStars
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalEntryDetailState
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalEntryViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEntryScreen(
    entryId: String,
    onBack: () -> Unit = {},
    onEdit: (entryId: String) -> Unit = {}
) {
    val viewModel: JournalEntryViewModel = koinInject { parametersOf(entryId) }
    val detailState by viewModel.detailState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasting Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(entryId) }) {
                        Text("Edit")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val s = detailState) {
                is JournalEntryDetailState.Loading -> GoodwinesLoadingIndicator()
                is JournalEntryDetailState.Error -> GoodwinesErrorView(message = s.message)
                is JournalEntryDetailState.Loaded -> EntryDetailContent(entry = s.entry)
            }
        }
    }
}

@Composable
private fun EntryDetailContent(entry: TastingEntry) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Wine header
        Text(
            text = entry.wine.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = entry.wine.winery,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (entry.wine.vintage != null) {
            Text(
                text = "${entry.wine.vintage} · ${entry.wine.region}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else if (entry.wine.region.isNotBlank()) {
            Text(
                text = entry.wine.region,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(16.dp))
        RatingStars(rating = entry.rating, starSize = 28.dp)

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        if (entry.notes.isNotBlank()) {
            DetailSection(label = "Tasting Notes", value = entry.notes)
        }
        if (!entry.foodPairing.isNullOrBlank()) {
            DetailSection(label = "Food Pairing", value = entry.foodPairing)
        }
        if (!entry.mood.isNullOrBlank()) {
            DetailSection(label = "Mood / Occasion", value = entry.mood)
        }
        if (!entry.location.isNullOrBlank()) {
            DetailSection(label = "Location", value = entry.location)
        }

        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = entry.wine.style.name.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            if (entry.isPublic) {
                Text(
                    text = "Public",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
private fun DetailSection(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(2.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
