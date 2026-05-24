package com.lasecun.goodwines.features.journal.domain.usecase

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class GetJournalEntriesUseCase(
    private val repository: TastingEntryRepository
) {
    suspend operator fun invoke(userId: String): List<TastingEntry> =
        repository.getEntriesByUser(userId)
}
