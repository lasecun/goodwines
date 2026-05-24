package com.lasecun.goodwines.features.wine.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WineListUiState(
    val searchQuery: String = "",
    val wines: List<Wine> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class WineListViewModel(
    private val wineRepository: WineRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WineListUiState())
    val state: StateFlow<WineListUiState> = _state.asStateFlow()

    init {
        loadAllWines()
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        if (query.isNotBlank()) {
            searchWines(query)
        } else {
            loadAllWines()
        }
    }

    private fun loadAllWines() {
        searchWines("")
    }

    private fun searchWines(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                val results = wineRepository.searchWines(query)
                _state.value = _state.value.copy(
                    wines = results,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
