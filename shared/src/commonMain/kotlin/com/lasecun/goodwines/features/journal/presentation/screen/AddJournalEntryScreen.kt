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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.presentation.components.GoodwinesButton
import com.lasecun.goodwines.core.presentation.components.GoodwinesLoadingIndicator
import com.lasecun.goodwines.core.presentation.components.RatingStars
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalEntryFormState
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalEntryViewModel
import com.lasecun.goodwines.features.wine.domain.model.WineStyle
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

/**
 * Add or edit a tasting journal entry.
 *
 * When [entryId] is null or blank the user is creating a new entry.
 * When [entryId] is provided the existing entry is loaded and the form
 * is pre-filled for editing.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalEntryScreen(
    entryId: String? = null,
    prefillWineName: String = "",
    prefillWineWinery: String = "",
    onBack: () -> Unit = {},
    onSaved: () -> Unit = {}
) {
    val viewModel: JournalEntryViewModel = koinInject { parametersOf(entryId?.ifBlank { null }) }
    val formState by viewModel.formState.collectAsState()

    // Apply scanner pre-fill for new entries (entryId is null)
    LaunchedEffect(prefillWineName, prefillWineWinery) {
        if (entryId.isNullOrBlank()) {
            if (prefillWineName.isNotBlank()) viewModel.updateWineName(prefillWineName)
            if (prefillWineWinery.isNotBlank()) viewModel.updateWineWinery(prefillWineWinery)
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigate back once saved
    LaunchedEffect(formState) {
        if (formState is JournalEntryFormState.Saved) {
            onSaved()
        }
        if (formState is JournalEntryFormState.Error) {
            snackbarHostState.showSnackbar((formState as JournalEntryFormState.Error).message)
            viewModel.clearError()
        }
    }

    val title = if (entryId.isNullOrBlank()) "Log Wine" else "Edit Entry"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Text("←") }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (formState is JournalEntryFormState.Saving) {
            Box(modifier = Modifier.fillMaxSize()) { GoodwinesLoadingIndicator() }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FormSectionHeader("Wine")
            WineNameField(viewModel)
            WineryField(viewModel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VintageField(viewModel, modifier = Modifier.weight(1f))
                WineRegionField(viewModel, modifier = Modifier.weight(2f))
            }
            WineStyleSelector(viewModel)

            Spacer(Modifier.height(4.dp))
            HorizontalDivider()

            FormSectionHeader("Your Tasting")
            RatingSection(viewModel)

            NotesField(viewModel)

            Spacer(Modifier.height(4.dp))
            HorizontalDivider()

            FormSectionHeader("Optional Details")
            FoodPairingField(viewModel)
            MoodField(viewModel)
            LocationField(viewModel)
            PublicToggle(viewModel)

            Spacer(Modifier.height(16.dp))

            // Action buttons
            GoodwinesButton(
                text = "Save Entry",
                onClick = { viewModel.saveEntry() },
                modifier = Modifier.fillMaxWidth()
            )
            GoodwinesButton(
                text = "Save as Draft",
                onClick = { viewModel.saveDraft() },
                modifier = Modifier.fillMaxWidth(),
                isLoading = false
            )
        }
    }
}

@Composable
private fun FormSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun WineNameField(viewModel: JournalEntryViewModel) {
    val value by viewModel.wineName.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateWineName,
        label = { Text("Wine Name *") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun WineryField(viewModel: JournalEntryViewModel) {
    val value by viewModel.wineWinery.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateWineWinery,
        label = { Text("Winery *") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun VintageField(viewModel: JournalEntryViewModel, modifier: Modifier = Modifier) {
    val value by viewModel.wineVintage.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateWineVintage,
        label = { Text("Vintage") },
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
private fun WineRegionField(viewModel: JournalEntryViewModel, modifier: Modifier = Modifier) {
    val value by viewModel.wineRegion.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateWineRegion,
        label = { Text("Region") },
        modifier = modifier,
        singleLine = true
    )
}

@Composable
private fun WineStyleSelector(viewModel: JournalEntryViewModel) {
    val selected by viewModel.wineStyle.collectAsState()
    Column {
        Text(
            text = "Style",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WineStyle.entries.forEach { style ->
                val isSelected = style == selected
                androidx.compose.material3.FilterChip(
                    selected = isSelected,
                    onClick = { viewModel.updateWineStyle(style) },
                    label = {
                        Text(
                            text = style.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun RatingSection(viewModel: JournalEntryViewModel) {
    val rating by viewModel.rating.collectAsState()
    Column {
        Text(
            text = "Rating",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        RatingStars(
            rating = rating,
            onRate = { stars -> viewModel.updateRating(stars.toFloat()) },
            starSize = 32.dp
        )
    }
}

@Composable
private fun NotesField(viewModel: JournalEntryViewModel) {
    val value by viewModel.notes.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateNotes,
        label = { Text("Tasting Notes") },
        modifier = Modifier.fillMaxWidth().height(120.dp),
        maxLines = 5
    )
}

@Composable
private fun FoodPairingField(viewModel: JournalEntryViewModel) {
    val value by viewModel.foodPairing.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateFoodPairing,
        label = { Text("Food Pairing") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun MoodField(viewModel: JournalEntryViewModel) {
    val value by viewModel.mood.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateMood,
        label = { Text("Mood / Occasion") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun LocationField(viewModel: JournalEntryViewModel) {
    val value by viewModel.location.collectAsState()
    OutlinedTextField(
        value = value,
        onValueChange = viewModel::updateLocation,
        label = { Text("Location") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
private fun PublicToggle(viewModel: JournalEntryViewModel) {
    val isPublic by viewModel.isPublic.collectAsState()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isPublic,
            onCheckedChange = viewModel::updateIsPublic
        )
        Text(
            text = "Share publicly in activity feed",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
