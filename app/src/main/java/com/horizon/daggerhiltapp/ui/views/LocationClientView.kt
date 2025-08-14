package com.horizon.daggerhiltapp.ui.views

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.horizon.daggerhiltapp.ui.viewmodel.LocationViewModel

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(viewModel: LocationViewModel, navController: NavController) {

    val location by viewModel.location.collectAsState()
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(locationPermission.status) {
        if (locationPermission.status.isGranted) {
            viewModel.loadLocation()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                },
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
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nota: La función getLastKnowLocation no garantiza devolver un valor. " +
                    "Sólo da la útima ubicación que Android tenga en el cache. Si el dispositivo nunca obtuvo una ubicación antes, devuelve null." +
                        "Entra a una app como GoogleMaps para que obtenga tu ubicación y así, esta se guardará en el cache y aparecerá aquí."
            )
            Spacer(Modifier.height(80.dp))
            if (locationPermission.status.isGranted) {
                Text("Lat: ${location?.latitude}, Lng: ${location?.longitude}")
            } else {
                Button(onClick = { locationPermission.launchPermissionRequest() }) {
                    Text("Pedir permiso de ubicación")
                }
            }
            if (locationPermission.status.isGranted) {
                Button(onClick = { viewModel.getLastKnowLocate() }) {
                    Text("Actualizar ubicación")
                }
            } else {
                Button(onClick = { locationPermission.launchPermissionRequest() }) {
                    Text("Dar permiso")
                }
            }
        }
    }
}

@Composable
private fun FakeLocation() {

}