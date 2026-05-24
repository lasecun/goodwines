package com.lasecun.goodwines.features.journal.presentation.viewmodel

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry

sealed interface JournalUiState {
    data object Loading : JournalUiState
    data class Success(val entries: List<TastingEntry>) : JournalUiState
    data class Error(val message: String) : JournalUiState
}

sealed interface JournalEntryDetailState {
    data object Loading : JournalEntryDetailState
    data class Loaded(val entry: TastingEntry) : JournalEntryDetailState
    data class Error(val message: String) : JournalEntryDetailState
}

sealed interface JournalEntryFormState {
    data object Idle : JournalEntryFormState
    data object Loading : JournalEntryFormState
    data object Saving : JournalEntryFormState
    data object Saved : JournalEntryFormState
    data class Error(val message: String) : JournalEntryFormState
}
