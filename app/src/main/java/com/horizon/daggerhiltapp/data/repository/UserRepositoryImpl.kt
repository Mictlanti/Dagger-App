package com.horizon.daggerhiltapp.data.repository

import com.horizon.daggerhiltapp.data.local.dao.UserDao
import com.horizon.daggerhiltapp.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: UserEntity) : Long {
        return userDao.insertUser(user)
    }

    fun getAllUsers() : Flow<List<UserEntity>> = userDao.getAllUsers()

    fun getUserById(idDoc: Int) = userDao.getUserById(idDoc)

    suspend fun deleteUser(idUser: Int) = userDao.deleteUser(idUser)

    suspend fun deleteAll() = userDao.deleteAll()
}