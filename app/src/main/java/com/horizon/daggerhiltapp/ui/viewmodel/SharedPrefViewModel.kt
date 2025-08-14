package com.horizon.daggerhiltapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.horizon.daggerhiltapp.domain.repository.PreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SharedData(
    val username: String = ""
)

@HiltViewModel
class SharedPrefViewModel @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) : ViewModel(){

    private val _state = MutableStateFlow(SharedData())
    val state : StateFlow<SharedData> = _state.asStateFlow()

    fun saveUser(name: String){
        _state.value = _state.value.copy(
            username = name
        )
        preferencesRepo.saveUserName(name)
    }

    fun loadName() {
        _state.value = _state.value.copy(
            username = preferencesRepo.getUserName()
        )
    }

}