package com.horizon.daggerhiltapp.Services

import com.horizon.daggerhiltapp.data.Post
import retrofit2.http.GET

interface FakeApiService {

    @GET("/posts")
    suspend fun getPost() : List<Post>

}