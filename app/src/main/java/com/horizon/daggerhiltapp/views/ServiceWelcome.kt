package com.horizon.daggerhiltapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.horizon.daggerhiltapp.viewmodel.GreetingViewModel
import javax.inject.Inject

interface GreetingService {
    fun greet() : String
    fun farewell() : String
}

class GreetingServiceImpl @Inject constructor() : GreetingService {
    override fun greet(): String = "Hola desde Hilt"

    override fun farewell(): String = "Adelante amigos"
}

@Composable
fun GreetingScreen(viewModel: GreetingViewModel, pad: PaddingValues) {

    val state = viewModel.greeting.collectAsState()
    val farewell = viewModel.farewell.value

    LaunchedEffect(Unit) {
        viewModel.loadFarewell()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(pad)
    ) {
        Text(text = state.value, fontSize = 25.sp)
        Text(text = farewell, fontSize = 25.sp)
    }

}