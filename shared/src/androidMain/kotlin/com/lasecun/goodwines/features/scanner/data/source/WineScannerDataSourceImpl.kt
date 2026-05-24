package com.lasecun.goodwines.features.scanner.data.source

import com.lasecun.goodwines.core.platform.randomUuid
import com.lasecun.goodwines.features.scanner.domain.model.ScanResult

/**
 * Android implementation of [WineScannerDataSource].
 *
 * MVP: wraps raw image bytes in a [ScanResult] without OCR.
 * OCR / label recognition is delegated to the backend GraphQL API
 * in a later phase — the client will POST the image and receive
 * structured wine metadata in return.
 */
class WineScannerDataSourceImpl : WineScannerDataSource {
    override suspend fun processScan(imageBytes: ByteArray): ScanResult {
        return ScanResult(
            scanId = randomUuid(),
            imageBytes = imageBytes,
            suggestedName = "",
            suggestedWinery = "",
            confidence = 0f
        )
    }
}
