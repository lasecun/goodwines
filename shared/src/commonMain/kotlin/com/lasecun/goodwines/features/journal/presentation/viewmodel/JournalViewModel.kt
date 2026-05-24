package com.lasecun.goodwines.features.journal.presentation.viewmodel

import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthUiState
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthViewModel
import com.lasecun.goodwines.features.journal.domain.usecase.DeleteTastingEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.GetJournalEntriesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Manages the journal list screen state.
 * Reads the current userId from [AuthViewModel] so the journal is always
 * scoped to the signed-in user without duplicating session state.
 */
class JournalViewModel(
    private val getJournalEntriesUseCase: GetJournalEntriesUseCase,
    private val deleteEntryUseCase: DeleteTastingEntryUseCase,
    private val authViewModel: AuthViewModel
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<JournalUiState>(JournalUiState.Loading)
    val state: StateFlow<JournalUiState> = _state.asStateFlow()

    init {
        loadEntries()
    }

    fun loadEntries() {
        val userId = currentUserId() ?: return
        scope.launch {
            _state.value = JournalUiState.Loading
            runCatching { getJournalEntriesUseCase(userId) }
                .onSuccess { _state.value = JournalUiState.Success(it) }
                .onFailure { _state.value = JournalUiState.Error(it.message ?: "Failed to load journal") }
        }
    }

    fun deleteEntry(id: String) {
        scope.launch {
            deleteEntryUseCase(id)
            loadEntries()
        }
    }

    private fun currentUserId(): String? =
        (authViewModel.authState.value as? AuthUiState.LoggedIn)?.session?.userId
}
