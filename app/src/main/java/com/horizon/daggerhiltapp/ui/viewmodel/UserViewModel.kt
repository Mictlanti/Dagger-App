package com.horizon.daggerhiltapp.ui.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.daggerhiltapp.data.local.entities.UserEntity
import com.horizon.daggerhiltapp.data.repository.UserRepositoryImpl
import com.horizon.daggerhiltapp.domain.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userRepositoryImpl.getAllUsers().collect { user ->
                _state.value = _state.value.copy(userList = user)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun saveUser(name: String, age: String) {

        val filtered = age.filter { it.isDigit() }

        viewModelScope.launch {
            val user = UserEntity(name = name, age = filtered.toIntOrNull() ?: 0)
            val idGenerate = userRepositoryImpl.insertUser(user)

            _state.value = _state.value.copy(
                idDoc = idGenerate.toInt(),
                username = user.name,
                age = user.age
            )
            Log.d("UserView", "idUser: $idGenerate")
        }
    }

    suspend fun getUserById(idDoc: Int) {

        val user = userRepositoryImpl.getUserById(idDoc).firstOrNull()

        return if(user != null){
            _state.update { state ->
                state.copy(
                    idDoc = user.id,
                    username = user.name,
                    age = user.age
                )
            }
        } else {
            _state.update { state ->
                state.copy(
                    idDoc = 0,
                    username = "",
                    age = 0
                )
            }
        }

    }

    fun deleteUser() {

        val idUser = _state.value.idDoc

        viewModelScope.launch {
            userRepositoryImpl.deleteUser(idUser)
            Log.d(
                "UserViewModel",
                "idUser: $idUser, delete: ${userRepositoryImpl.deleteUser(idUser)}"
            )
            _state.value = _state.value.copy(
                username = "", age = 0
            )
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch {
            userRepositoryImpl.deleteAll()
        }
    }

}