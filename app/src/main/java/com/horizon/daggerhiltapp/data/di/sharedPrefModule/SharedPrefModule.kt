package com.horizon.daggerhiltapp.data.di.sharedPrefModule

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    //Creamos una instancia única para toda la app
    @Provides
    @Singleton
    fun providesSharedPref(@ApplicationContext context : Context) : SharedPreferences {
        return context.getSharedPreferences("my_refs", Context.MODE_PRIVATE)
    }

    //Obtenemos un editor para guardar datos
    @Provides
    @Singleton
    fun providesSharedPrefEditor(
        sharedPreferences: SharedPreferences
    ) : SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}