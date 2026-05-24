package com.lasecun.goodwines.features.journal.domain.usecase

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class GetJournalEntryUseCase(
    private val repository: TastingEntryRepository
) {
    suspend operator fun invoke(id: String): TastingEntry? =
        repository.getEntryById(id)
}
