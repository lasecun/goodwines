package com.lasecun.goodwines.features.journal.di

import com.lasecun.goodwines.features.journal.data.repository.TastingEntryRepositoryImpl
import com.lasecun.goodwines.features.journal.data.source.remote.RemoteJournalDataSource
import com.lasecun.goodwines.features.journal.data.source.remote.RemoteJournalDataSourceImpl
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository
import com.lasecun.goodwines.features.journal.domain.usecase.DeleteTastingEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.GetJournalEntriesUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.GetJournalEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.SaveDraftEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.SaveTastingEntryUseCase
import com.lasecun.goodwines.features.journal.domain.usecase.UpdateTastingEntryUseCase
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalEntryViewModel
import com.lasecun.goodwines.features.journal.presentation.viewmodel.JournalViewModel
import org.koin.dsl.module

val journalModule = module {
    single<RemoteJournalDataSource> { RemoteJournalDataSourceImpl() }
    single<TastingEntryRepository> { TastingEntryRepositoryImpl(get(), get()) }

    // Use cases
    factory { GetJournalEntriesUseCase(get()) }
    factory { GetJournalEntryUseCase(get()) }
    factory { SaveTastingEntryUseCase(get()) }
    factory { UpdateTastingEntryUseCase(get()) }
    factory { DeleteTastingEntryUseCase(get()) }
    factory { SaveDraftEntryUseCase(get()) }

    // ViewModels — factory so each screen gets its own scope
    single { JournalViewModel(get(), get(), get()) }
    factory { (entryId: String?) -> JournalEntryViewModel(entryId, get(), get(), get(), get(), get()) }
}
