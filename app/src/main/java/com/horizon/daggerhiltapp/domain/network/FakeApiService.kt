package com.horizon.daggerhiltapp.domain.network

import com.horizon.daggerhiltapp.domain.model.Post
import retrofit2.http.GET

//Interfaz de Retrofit que define cómo se hacen las llamadas HTTP a la Api
//@GET("/post") Indica que se ahrá una petición GET al endpoint /post
//Devuelve List<Post> -> Retrofit permite convertir el Json en objetos Post usando Gson

interface FakeApiService {

    @GET("/posts")
    suspend fun getPost() : List<Post>

}