package com.horizon.daggerhiltapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horizon.daggerhiltapp.data.local.dao.UserDao
import com.horizon.daggerhiltapp.data.local.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}