package com.horizon.daggerhiltapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.daggerhiltapp.domain.model.Post
import com.horizon.daggerhiltapp.domain.repository.GreetingRepo
import com.horizon.daggerhiltapp.domain.repository.PostRepo
import com.horizon.daggerhiltapp.ui.views.GreetingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    private val greetingService: GreetingService,
    private val greetingRepo: GreetingRepo,
    private val repoPost : PostRepo
) : ViewModel() {

    //Usamos StateFlow para exponer cambios reactivos de la UI, de modo que Compose se reactive cuando cambien
    private val _greeting = MutableStateFlow("")
    val greeting : StateFlow<String> = _greeting
    val farewell = mutableStateOf("")

    private val _post = MutableStateFlow<List<Post>>(emptyList())
    val post : StateFlow<List<Post>> = _post.asStateFlow()

    init {
        _greeting.value = greetingRepo.getGreeting()
        fetchPosts()
    }

    fun loadFarewell() {
        farewell.value = greetingService.farewell()
    }

    //viewModelScope.launch ejecuta la llamada a la API en segundo plano
    //Si la API devuelve datos, mostramos el título del primer post.
    //Si falla, mostramos el error.
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val posts = repoPost.getPost()
                _greeting.value = "Primer post: ${posts.firstOrNull()?.title ?: "Sin datos"}"
            } catch (e: Exception) {
                _greeting.value = "Error: ${e.message}"
            }
        }
    }

}