package com.horizon.daggerhiltapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.horizon.daggerhiltapp.navigation.Navigation
import com.horizon.daggerhiltapp.ui.theme.DaggerHiltAppTheme
import com.horizon.daggerhiltapp.ui.viewmodel.GreetingViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.LocationViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.SharedPrefViewModel
import com.horizon.daggerhiltapp.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel : GreetingViewModel = hiltViewModel()
            val sharedPrefViewModel : SharedPrefViewModel = hiltViewModel()
            val locationViewModel: LocationViewModel = hiltViewModel()
            val userViewModel : UserViewModel = hiltViewModel()

            DaggerHiltAppTheme {
                    Navigation(viewModel, sharedPrefViewModel, locationViewModel, userViewModel)
            }
        }
    }
}