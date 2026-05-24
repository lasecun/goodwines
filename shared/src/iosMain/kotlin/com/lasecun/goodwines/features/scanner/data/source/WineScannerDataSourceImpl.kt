package com.lasecun.goodwines.features.scanner.data.source

import com.lasecun.goodwines.core.platform.randomUuid
import com.lasecun.goodwines.features.scanner.domain.model.ScanResult

/**
 * iOS implementation of [WineScannerDataSource].
 * Same MVP behaviour as Android — no OCR yet.
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
