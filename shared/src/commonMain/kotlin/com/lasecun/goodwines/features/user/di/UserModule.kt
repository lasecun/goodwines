package com.lasecun.goodwines.features.user.di

import com.lasecun.goodwines.features.user.data.repository.UserRepositoryImpl
import com.lasecun.goodwines.features.user.data.source.remote.RemoteUserDataSource
import com.lasecun.goodwines.features.user.data.source.remote.RemoteUserDataSourceImpl
import com.lasecun.goodwines.features.user.domain.repository.UserRepository
import com.lasecun.goodwines.features.user.domain.usecase.GetCurrentUserProfileUseCase
import com.lasecun.goodwines.features.user.presentation.viewmodel.UserProfileViewModel
import org.koin.dsl.module

val userModule = module {
    single<RemoteUserDataSource> { RemoteUserDataSourceImpl() }
    single<UserRepository> { UserRepositoryImpl(get()) }
    factory { GetCurrentUserProfileUseCase(get(), get()) }
    factory { UserProfileViewModel(get()) }
}
