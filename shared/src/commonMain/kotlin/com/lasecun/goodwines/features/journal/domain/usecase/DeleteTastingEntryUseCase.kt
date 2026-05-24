package com.lasecun.goodwines.features.journal.domain.usecase

import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class DeleteTastingEntryUseCase(
    private val repository: TastingEntryRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> =
        runCatching { repository.deleteEntry(id) }
}
