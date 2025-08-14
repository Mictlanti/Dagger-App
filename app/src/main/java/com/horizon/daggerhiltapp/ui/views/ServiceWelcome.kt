package com.horizon.daggerhiltapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.daggerhiltapp.R
import com.horizon.daggerhiltapp.ui.viewmodel.GreetingViewModel
import javax.inject.Inject

interface GreetingService {
    fun greet(): String
    fun farewell(): String
}

class GreetingServiceImpl @Inject constructor() : GreetingService {
    override fun greet(): String = "Hola desde Hilt"

    override fun farewell(): String = "Adelante amigos"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetingScreen(viewModel: GreetingViewModel, navController: NavController) {

    val state = viewModel.greeting.collectAsState()
    val farewell = viewModel.farewell.value
    val conexionDaggerApi = listOf(
        "NetworkModule provee las dependencias de red(Gson, LogginInterceptor, OkHTTPClient, Retrofit)",
        "Retrofit crea el FakeApiService, que se inyecta en PostRepo",
        "PostRepo, junto con GreetingRepo y GreetingService, se inyecta en GreetingViewModel",
        "Finalmente, el ViewModel exponen datos para la UI en Compose"
    )

    LaunchedEffect(Unit) {
        viewModel.loadFarewell()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(text = state.value, fontSize = 25.sp)
                Text(text = farewell, fontSize = 25.sp)
            }
            item {
                Image(
                    painter = painterResource(R.drawable.diagrama_conexion_fake_api),
                    "Image",
                    modifier = Modifier.aspectRatio(1f)
                )
            }
            items(conexionDaggerApi) { s ->
                Text(text = s, fontSize = 20.sp)
            }

        }
    }

}