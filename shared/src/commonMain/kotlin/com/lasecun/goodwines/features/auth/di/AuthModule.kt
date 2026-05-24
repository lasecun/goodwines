package com.lasecun.goodwines.features.auth.di

import com.lasecun.goodwines.features.auth.data.repository.AuthRepositoryImpl
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSourceImpl
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository
import org.koin.dsl.module

val authModule = module {
    single<RemoteAuthDataSource> { RemoteAuthDataSourceImpl() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
