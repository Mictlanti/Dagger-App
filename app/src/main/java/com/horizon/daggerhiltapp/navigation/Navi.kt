package com.horizon.daggerhiltapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.horizon.daggerhiltapp.ui.viewmodel.GreetingViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.LocationViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.SharedPrefViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.UserViewModel
import com.horizon.daggerhiltapp.ui.views.GreetingScreen
import com.horizon.daggerhiltapp.ui.views.HomeViewRoute
import com.horizon.daggerhiltapp.ui.views.LocationScreen
import com.horizon.daggerhiltapp.ui.views.SharedPrefView
import com.horizon.daggerhiltapp.ui.views.UserViewRoute

@Composable
fun Navigation(
    viewModel: GreetingViewModel,
    sharedPrefViewModel: SharedPrefViewModel,
    locationViewModel: LocationViewModel,
    userViewModel: UserViewModel
) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = AppScreens.HomeView.route) {
        composable(AppScreens.HomeView.route) {
            HomeViewRoute(navController)
        }
        composable(AppScreens.GreetingAndFakeApiView.route) {
            GreetingScreen(viewModel, navController)
        }
        composable(AppScreens.SharedPrefView.route) {
            SharedPrefView(navController, sharedPrefViewModel)
        }
        composable(AppScreens.LocationClientView.route) {
            LocationScreen(locationViewModel, navController)
        }
        composable(AppScreens.UserRoomView.route) {
            UserViewRoute(navController, userViewModel)
        }
    }

}