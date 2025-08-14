package com.horizon.daggerhiltapp.data.di.greeting

import com.horizon.daggerhiltapp.ui.views.GreetingService
import com.horizon.daggerhiltapp.ui.views.GreetingServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GreetingModule {

    @Provides
    fun provideGreetingModuleService() : GreetingService = GreetingServiceImpl()

}