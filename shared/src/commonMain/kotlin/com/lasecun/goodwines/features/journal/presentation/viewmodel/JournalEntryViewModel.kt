package com.lasecun.goodwines.features.journal.presentation.viewmodel

import com.lasecun.goodwines.core.platform.currentTimeMillis
import com.lasecun.goodwines.core.platform.randomUuid
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthUiState
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthViewModel
import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.usecase.GetJournalEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.SaveDraftEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.SaveTastingEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.UpdateTastingEntryUseCase
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.model.WineStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Manages the add/edit journal entry form state.
 * When [entryId] is null the user is creating a new entry.
 * When [entryId] is non-null the existing entry is loaded for editing.
 */
class JournalEntryViewModel(
    private val entryId: String?,
    private val getEntryUseCase: GetJournalEntryUseCase,
    private val saveEntryUseCase: SaveTastingEntryUseCase,
    private val updateEntryUseCase: UpdateTastingEntryUseCase,
    private val saveDraftUseCase: SaveDraftEntryUseCase,
    private val authViewModel: AuthViewModel
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _detailState = MutableStateFlow<JournalEntryDetailState>(JournalEntryDetailState.Loading)
    val detailState: StateFlow<JournalEntryDetailState> = _detailState.asStateFlow()

    private val _formState = MutableStateFlow<JournalEntryFormState>(JournalEntryFormState.Idle)
    val formState: StateFlow<JournalEntryFormState> = _formState.asStateFlow()

    // Editable form fields
    private val _wineName = MutableStateFlow("")
    val wineName: StateFlow<String> = _wineName.asStateFlow()

    private val _wineWinery = MutableStateFlow("")
    val wineWinery: StateFlow<String> = _wineWinery.asStateFlow()

    private val _wineVintage = MutableStateFlow("")
    val wineVintage: StateFlow<String> = _wineVintage.asStateFlow()

    private val _wineRegion = MutableStateFlow("")
    val wineRegion: StateFlow<String> = _wineRegion.asStateFlow()

    private val _wineStyle = MutableStateFlow(WineStyle.RED)
    val wineStyle: StateFlow<WineStyle> = _wineStyle.asStateFlow()

    private val _rating = MutableStateFlow(0f)
    val rating: StateFlow<Float> = _rating.asStateFlow()

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes.asStateFlow()

    private val _foodPairing = MutableStateFlow("")
    val foodPairing: StateFlow<String> = _foodPairing.asStateFlow()

    private val _mood = MutableStateFlow("")
    val mood: StateFlow<String> = _mood.asStateFlow()

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location.asStateFlow()

    private val _isPublic = MutableStateFlow(false)
    val isPublic: StateFlow<Boolean> = _isPublic.asStateFlow()

    // Preserved from the original entry when editing
    private var originalId: String = ""
    private var originalDate: Long = 0L
    private var originalWineId: String = ""

    init {
        if (entryId != null) {
            loadEntry(entryId)
        } else {
            _detailState.value = JournalEntryDetailState.Loaded(emptyEntry())
        }
    }

    private fun loadEntry(id: String) {
        scope.launch {
            _detailState.value = JournalEntryDetailState.Loading
            val entry = runCatching { getEntryUseCase(id) }.getOrNull()
            if (entry != null) {
                originalId = entry.id
                originalDate = entry.date
                originalWineId = entry.wine.id
                _wineName.value = entry.wine.name
                _wineWinery.value = entry.wine.winery
                _wineVintage.value = entry.wine.vintage?.toString() ?: ""
                _wineRegion.value = entry.wine.region
                _wineStyle.value = entry.wine.style
                _rating.value = entry.rating
                _notes.value = entry.notes
                _foodPairing.value = entry.foodPairing ?: ""
                _mood.value = entry.mood ?: ""
                _location.value = entry.location ?: ""
                _isPublic.value = entry.isPublic
                _detailState.value = JournalEntryDetailState.Loaded(entry)
            } else {
                _detailState.value = JournalEntryDetailState.Error("Entry not found")
            }
        }
    }

    fun updateWineName(value: String) { _wineName.value = value }
    fun updateWineWinery(value: String) { _wineWinery.value = value }
    fun updateWineVintage(value: String) { _wineVintage.value = value }
    fun updateWineRegion(value: String) { _wineRegion.value = value }
    fun updateWineStyle(value: WineStyle) { _wineStyle.value = value }
    fun updateRating(value: Float) { _rating.value = value }
    fun updateNotes(value: String) { _notes.value = value }
    fun updateFoodPairing(value: String) { _foodPairing.value = value }
    fun updateMood(value: String) { _mood.value = value }
    fun updateLocation(value: String) { _location.value = value }
    fun updateIsPublic(value: Boolean) { _isPublic.value = value }

    fun saveEntry() {
        val entry = buildEntry() ?: return
        scope.launch {
            _formState.value = JournalEntryFormState.Saving
            val result = if (entryId != null) updateEntryUseCase(entry) else saveEntryUseCase(entry)
            result
                .onSuccess { _formState.value = JournalEntryFormState.Saved }
                .onFailure { _formState.value = JournalEntryFormState.Error(it.message ?: "Save failed") }
        }
    }

    fun saveDraft() {
        val entry = buildEntry() ?: return
        scope.launch {
            _formState.value = JournalEntryFormState.Saving
            saveDraftUseCase(entry)
                .onSuccess { _formState.value = JournalEntryFormState.Saved }
                .onFailure { _formState.value = JournalEntryFormState.Error(it.message ?: "Draft save failed") }
        }
    }

    fun clearError() {
        _formState.value = JournalEntryFormState.Idle
    }

    private fun buildEntry(): TastingEntry? {
        val userId = (authViewModel.authState.value as? AuthUiState.LoggedIn)?.session?.userId
            ?: return null
        return TastingEntry(
            id = if (originalId.isNotEmpty()) originalId else randomUuid(),
            userId = userId,
            wine = Wine(
                id = if (originalWineId.isNotEmpty()) originalWineId else randomUuid(),
                name = _wineName.value.trim(),
                winery = _wineWinery.value.trim(),
                vintage = _wineVintage.value.toIntOrNull(),
                region = _wineRegion.value.trim(),
                style = _wineStyle.value
            ),
            rating = _rating.value,
            notes = _notes.value.trim(),
            date = if (originalDate != 0L) originalDate else currentTimeMillis(),
            location = _location.value.trim().ifEmpty { null },
            foodPairing = _foodPairing.value.trim().ifEmpty { null },
            mood = _mood.value.trim().ifEmpty { null },
            isPublic = _isPublic.value
        )
    }

    private fun emptyEntry(): TastingEntry = TastingEntry(
        id = "",
        userId = "",
        wine = Wine(id = "", name = "", winery = ""),
        rating = 0f,
        date = currentTimeMillis()
    )
}
