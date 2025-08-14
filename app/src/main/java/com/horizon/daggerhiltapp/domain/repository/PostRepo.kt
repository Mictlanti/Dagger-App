package com.horizon.daggerhiltapp.domain.repository

import com.horizon.daggerhiltapp.domain.network.FakeApiService
import javax.inject.Inject

//Este es el repositoria que hace de intermediario entre la API y el ViewModel.
//Recive el FakeApiService gracias a Hilt(@Inject constructor)
//Encapsula la lógica de ontención de datos.
class PostRepo @Inject constructor(
    private val api: FakeApiService
) {
    suspend fun getPost() = api.getPost()
}