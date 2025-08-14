package com.horizon.daggerhiltapp.domain.repository

import javax.inject.Inject

class GreetingRepo @Inject constructor() {

    fun getGreeting() : String {
        return "Hi from GreetingRepo"
    }

}