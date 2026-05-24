package com.lasecun.goodwines.features.scanner.presentation.viewmodel

import com.lasecun.goodwines.features.scanner.domain.model.ScanResult

sealed interface ScannerUiState {
    /** Waiting for the user to trigger the camera. */
    data object Idle : ScannerUiState

    /** Camera is active — platform composable is showing. */
    data object Scanning : ScannerUiState

    /** Image captured, processing scan result. */
    data object Processing : ScannerUiState

    /** Scan complete — ready to proceed to journal entry. */
    data class Ready(val result: ScanResult) : ScannerUiState

    /** User cancelled the scan. */
    data object Cancelled : ScannerUiState

    /** An error occurred during scanning or processing. */
    data class Error(val message: String) : ScannerUiState
}
