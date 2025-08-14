package com.horizon.daggerhiltapp.modules.greetingModule

import com.horizon.daggerhiltapp.views.GreetingService
import com.horizon.daggerhiltapp.views.GreetingServiceImpl
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