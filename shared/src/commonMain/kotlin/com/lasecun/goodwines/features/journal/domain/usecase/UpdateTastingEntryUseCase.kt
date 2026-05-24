package com.lasecun.goodwines.features.journal.domain.usecase

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class UpdateTastingEntryUseCase(
    private val repository: TastingEntryRepository
) {
    suspend operator fun invoke(entry: TastingEntry): Result<Unit> {
        if (entry.wine.name.isBlank())
            return Result.failure(IllegalArgumentException("Wine name is required"))
        if (entry.wine.winery.isBlank())
            return Result.failure(IllegalArgumentException("Winery is required"))
        if (entry.rating < 0f || entry.rating > 5f)
            return Result.failure(IllegalArgumentException("Rating must be between 0 and 5"))
        return runCatching { repository.updateEntry(entry) }
    }
}
