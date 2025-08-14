package com.horizon.daggerhiltapp.ui.views

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.horizon.daggerhiltapp.domain.state.UserState
import com.horizon.daggerhiltapp.ui.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserViewRoute(navController: NavController, userViewModel: UserViewModel) {

    val state by userViewModel.state.collectAsState()
    val name = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val itemsSelected = remember { mutableStateOf<Int?>(null) }
    val clickablesCount = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Room") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { userViewModel.deleteAllUser() }
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Button(
                    onClick = {
                        if (name.value.isBlank() && age.value.isBlank()) {
                            Toast.makeText(context, "Campos vacíos", Toast.LENGTH_SHORT).show()
                        } else {
                            userViewModel.saveUser(name.value, age.value)
                            name.value = ""
                            age.value = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar en Room")
                }
            }
        },
        floatingActionButton = {
            FloatingActionBtn {
                userViewModel.deleteUser()
                itemsSelected.value = null
                clickablesCount.value = 0
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DataUserView(userViewModel, state, clickablesCount, itemsSelected, scope)
            Spacer(Modifier.weight(2f))
            Text("Agregar usuario")
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Insert name") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(10.dp))
                TextField(
                    age.value,
                    onValueChange = { age.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Insert age") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun DataUserView(
    userViewModel: UserViewModel,
    state: UserState,
    clickablesCount: MutableState<Int>,
    itemsSelected: MutableState<Int?>,
    scope : CoroutineScope
) {

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .scrollable(scroll, orientation = Orientation.Vertical)
            .aspectRatio(1.2f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        state.userList.forEachIndexed { index, s ->
            TextButton(
                onClick = {
                    scope.launch {
                        clickablesCount.value++
                        if (clickablesCount.value <= 1) {
                            itemsSelected.value = index
                            userViewModel.getUserById(s.id)
                        } else {
                            clickablesCount.value = 0
                            itemsSelected.value = null
                        }
                    }
                }
            ) {
                Text(
                    "Hola: ${s.name}, con edad: ${s.age}",
                    color = if (itemsSelected.value == index) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun FloatingActionBtn(onDelete: () -> Unit) {

    val visible = remember { mutableStateOf(false) }
    val listBtns = listOf(
        Icons.Default.Close,
        Icons.Default.Delete,
        Icons.Default.Edit,
        Icons.Default.Favorite
    )

    FloatingActionButton(
        onClick = { visible.value = true },
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 7.dp
        ),
        modifier = Modifier
            .animateContentSize()
            .wrapContentHeight()
    ) {
        if (visible.value) {
            Column(
                modifier = Modifier
                    .padding(7.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                listBtns.forEachIndexed { index, vector ->
                    IconButton(
                        onClick = {
                            when (index) {
                                0 -> visible.value = false
                                1 -> { onDelete() }
                            }
                        }
                    ) {
                        Icon(
                            vector,
                            null
                        )
                    }
                }
            }
        } else {
            Icon(
                Icons.Default.Add,
                "More options"
            )
        }
    }
}