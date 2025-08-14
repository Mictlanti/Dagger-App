package com.horizon.daggerhiltapp.navigation

sealed class AppScreens(val route: String) {

    data object GreetingAndFakeApiView : AppScreens("GreetingFakeApi")
    data object HomeView : AppScreens("HomeView")
    data object SharedPrefView : AppScreens("SharedPrefView")
    data object LocationClientView : AppScreens("LocationView")
    data object UserRoomView : AppScreens("UserView")

}