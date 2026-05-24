package com.lasecun.goodwines.features.journal.di

import com.lasecun.goodwines.features.journal.data.repository.TastingEntryRepositoryImpl
import com.lasecun.goodwines.features.journal.data.source.remote.RemoteJournalDataSource
import com.lasecun.goodwines.features.journal.data.source.remote.RemoteJournalDataSourceImpl
import com.lasecun.goodwines.features.journal.domain.repository.TastingEntryRepository
import org.koin.dsl.module

val journalModule = module {
    single<RemoteJournalDataSource> { RemoteJournalDataSourceImpl() }
    single<TastingEntryRepository> { TastingEntryRepositoryImpl(get(), get()) }
}
