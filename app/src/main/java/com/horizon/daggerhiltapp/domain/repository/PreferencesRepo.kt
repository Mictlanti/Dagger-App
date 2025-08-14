package com.horizon.daggerhiltapp.domain.repository

import android.content.SharedPreferences
import com.horizon.daggerhiltapp.ui.viewmodel.SharedData
import javax.inject.Inject

class PreferencesRepo @Inject constructor(
    private val prefs: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    private val username = SharedData()

    fun saveUserName(name: String) {
        editor.putString("username", name).apply()
    }

    fun getUserName(): String {
        return prefs.getString("username", username.username) ?: ""
    }

}