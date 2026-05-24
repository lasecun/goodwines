package com.lasecun.goodwines.features.scanner.domain.model

/**
 * Result of a wine label scan attempt.
 *
 * [suggestedName] and [suggestedWinery] are populated when the OCR/ML
 * backend can extract text from the captured image. They are empty
 * strings when recognition is not available — the user fills them manually.
 */
data class ScanResult(
    val scanId: String,
    val imageBytes: ByteArray?,
    val suggestedName: String = "",
    val suggestedWinery: String = "",
    val suggestedVintage: String = "",
    val suggestedRegion: String = "",
    val confidence: Float = 0f
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as ScanResult
        return scanId == other.scanId
    }

    override fun hashCode(): Int = scanId.hashCode()
}
