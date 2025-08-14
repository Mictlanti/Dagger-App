package com.horizon.daggerhiltapp.repos

import javax.inject.Inject

class GreetingRepo @Inject constructor() {

    fun getGreeting() : String {
        return "Hi from GreetingRepo"
    }

}