package com.lasecun.goodwines.features.auth.di

import com.lasecun.goodwines.features.auth.data.repository.AuthRepositoryImpl
import com.lasecun.goodwines.features.auth.data.source.local.InMemorySessionDataSource
import com.lasecun.goodwines.features.auth.data.source.local.LocalSessionDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSource
import com.lasecun.goodwines.features.auth.data.source.remote.RemoteAuthDataSourceImpl
import com.lasecun.goodwines.features.auth.domain.repository.AuthRepository
import com.lasecun.goodwines.features.auth.domain.usecase.GetCurrentSessionUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.RegisterUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.SignInUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.SignOutUseCase
import com.lasecun.goodwines.features.auth.domain.usecase.StartDemoSessionUseCase
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    single<LocalSessionDataSource> { InMemorySessionDataSource() }
    single<RemoteAuthDataSource> { RemoteAuthDataSourceImpl() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    factory { SignInUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { SignOutUseCase(get()) }
    factory { GetCurrentSessionUseCase(get()) }
    factory { StartDemoSessionUseCase(get()) }

    single { AuthViewModel(get(), get(), get(), get(), get()) }
}
