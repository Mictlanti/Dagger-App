package com.horizon.daggerhiltapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.horizon.daggerhiltapp.navigation.AppScreens

@Composable
fun HomeViewRoute(navController: NavController) {

    val cardTheme = listOf(
        "Greeting and fake API" to AppScreens.GreetingAndFakeApiView.route,
        "SharedPreferences" to AppScreens.SharedPrefView.route,
        "LocationClient" to AppScreens.LocationClientView.route,
        "Room" to AppScreens.UserRoomView.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(cardTheme) { (title, route) ->
                CardSection(title, route, navController)
            }
        }
    }
}

@Composable
fun CardSection(text: String, route: String, navController: NavController) {
    ElevatedCard(
        onClick = { navController.navigate(route) },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            text,
            fontSize = 20.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(20.dp)
        )
    }
}