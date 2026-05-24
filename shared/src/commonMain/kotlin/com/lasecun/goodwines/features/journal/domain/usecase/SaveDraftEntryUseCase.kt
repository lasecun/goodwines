package com.lasecun.goodwines.features.journal.domain.usecase

import com.lasecun.goodwines.features.journal.domain.model.TastingEntry
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository

class SaveDraftEntryUseCase(
    private val repository: TastingEntryRepository
) {
    suspend operator fun invoke(entry: TastingEntry): Result<Unit> =
        runCatching { repository.saveDraft(entry) }
}
