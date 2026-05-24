package com.lasecun.goodwines.features.scanner.data.source

import com.lasecun.goodwines.features.scanner.domain.model.ScanResult

/**
 * Platform-neutral contract for wine label scanning.
 *
 * The platform implementations decide how image data is acquired
 * (camera, gallery, etc.). The shared scanner feature code only
 * depends on this interface — it never references CameraX, AVFoundation,
 * or any other platform SDK directly.
 */
interface WineScannerDataSource {
    /**
     * Processes raw image bytes and returns a [ScanResult].
     * For the MVP this may just wrap the raw bytes without OCR;
     * real OCR is delegated to the backend in a later phase.
     */
    suspend fun processScan(imageBytes: ByteArray): ScanResult
}
