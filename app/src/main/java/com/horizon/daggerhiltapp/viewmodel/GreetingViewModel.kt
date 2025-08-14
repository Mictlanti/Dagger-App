package com.horizon.daggerhiltapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horizon.daggerhiltapp.data.Post
import com.horizon.daggerhiltapp.repos.GreetingRepo
import com.horizon.daggerhiltapp.repos.PostRepo
import com.horizon.daggerhiltapp.views.GreetingService
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