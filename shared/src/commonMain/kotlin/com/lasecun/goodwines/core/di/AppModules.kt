package com.lasecun.goodwines.core.di

import com.lasecun.goodwines.features.auth.di.authModule
import com.lasecun.goodwines.features.journal.di.journalModule
import com.lasecun.goodwines.features.social.di.socialModule
import com.lasecun.goodwines.features.user.di.userModule
import com.lasecun.goodwines.features.wine.di.wineModule

// All Koin modules loaded at app startup.
val appModules = listOf(
    coreModule,
    authModule,
    wineModule,
    journalModule,
    userModule,
    socialModule
)
