package com.lasecun.goodwines.features.social.di

import com.lasecun.goodwines.features.social.data.repository.SocialRepositoryImpl
import com.lasecun.goodwines.features.social.data.source.remote.RemoteSocialDataSource
import com.lasecun.goodwines.features.social.data.source.remote.RemoteSocialDataSourceImpl
import com.lasecun.goodwines.features.social.domain.repository.SocialRepository
import com.lasecun.goodwines.features.social.domain.usecase.GetActivityFeedUseCase
import com.lasecun.goodwines.features.social.presentation.viewmodel.ActivityFeedViewModel
import org.koin.dsl.module

val socialModule = module {
    single<RemoteSocialDataSource> { RemoteSocialDataSourceImpl() }
    single<SocialRepository> { SocialRepositoryImpl(get()) }
    factory { GetActivityFeedUseCase(get(), get()) }
    factory { ActivityFeedViewModel(get()) }
}
