package com.horizon.daggerhiltapp.data.di

import android.content.Context
import androidx.room.Room
import com.horizon.daggerhiltapp.data.local.AppDatabase
import com.horizon.daggerhiltapp.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    fun providesUserDao(db: AppDatabase) : UserDao = db.userDao()

}