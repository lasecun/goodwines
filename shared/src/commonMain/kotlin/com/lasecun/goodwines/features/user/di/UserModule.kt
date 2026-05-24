package com.lasecun.goodwines.features.user.di

import com.lasecun.goodwines.features.user.data.repository.UserRepositoryImpl
import com.lasecun.goodwines.features.user.domain.repository.UserRepository
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}
