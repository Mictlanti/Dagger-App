package com.horizon.daggerhiltapp.domain.state

import com.horizon.daggerhiltapp.data.local.entities.UserEntity

data class UserState(
    val userList: List<UserEntity> = emptyList(),
    val idDoc: Int = 0,
    val username: String = "",
    val age : Int = 0
)