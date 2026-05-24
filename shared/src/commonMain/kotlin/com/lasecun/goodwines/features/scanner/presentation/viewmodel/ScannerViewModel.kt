package com.lasecun.goodwines.features.scanner.presentation.viewmodel

import com.lasecun.goodwines.features.scanner.data.source.WineScannerDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Manages the wine scanner flow state.
 *
 * Flow:
 * Idle → [startScan] → Scanning (camera active) → [onImageCaptured] →
 * Processing → Ready(ScanResult) → caller navigates to AddJournalEntry.
 *
 * The [WineScannerDataSource] is the only scanner dependency injected here.
 * All camera/image-picker concerns stay in the platform composable.
 */
class ScannerViewModel(
    private val scannerDataSource: WineScannerDataSource
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _state = MutableStateFlow<ScannerUiState>(ScannerUiState.Idle)
    val state: StateFlow<ScannerUiState> = _state.asStateFlow()

    fun startScan() {
        _state.value = ScannerUiState.Scanning
    }

    fun onImageCaptured(imageBytes: ByteArray?) {
        if (imageBytes == null) {
            _state.value = ScannerUiState.Cancelled
            return
        }
        _state.value = ScannerUiState.Processing
        scope.launch {
            runCatching { scannerDataSource.processScan(imageBytes) }
                .onSuccess { result -> _state.value = ScannerUiState.Ready(result) }
                .onFailure { error ->
                    _state.value = ScannerUiState.Error(
                        error.message ?: "Failed to process scan"
                    )
                }
        }
    }

    fun reset() {
        _state.value = ScannerUiState.Idle
    }
}
