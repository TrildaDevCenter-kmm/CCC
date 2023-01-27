package com.oztechan.ccc.client.di

import com.oztechan.ccc.client.repository.appconfig.AppConfigRepository
import com.oztechan.ccc.client.repository.appconfig.AppConfigRepositoryImpl
import com.oztechan.ccc.client.repository.background.BackgroundRepository
import com.oztechan.ccc.client.repository.background.BackgroundRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AppConfigRepositoryImpl) { bind<AppConfigRepository>() }
    singleOf(::BackgroundRepositoryImpl) { bind<BackgroundRepository>() }
}
