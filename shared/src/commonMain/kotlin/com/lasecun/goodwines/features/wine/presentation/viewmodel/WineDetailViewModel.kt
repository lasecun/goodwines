package com.lasecun.goodwines.features.wine.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.repository.WineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WineDetailUiState(
    val wine: Wine? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val toastMessage: String? = null
)

class WineDetailViewModel(
    private val wineRepository: WineRepository
) : ViewModel() {

    private val _state = MutableStateFlow(WineDetailUiState())
    val state: StateFlow<WineDetailUiState> = _state.asStateFlow()

    fun loadWine(wineId: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                val wine = wineRepository.getWineById(wineId)
                _state.value = _state.value.copy(
                    wine = wine,
                    isLoading = false,
                    error = if (wine == null) "Wine not found" else null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    fun markAsTasted() {
        _state.value = _state.value.copy(
            toastMessage = "Wine marked as tasted! Navigate to journal to add details."
        )
    }

    fun addToWishlist() {
        _state.value = _state.value.copy(
            toastMessage = "Added to wishlist!"
        )
    }

    fun clearToast() {
        _state.value = _state.value.copy(toastMessage = null)
    }
}
