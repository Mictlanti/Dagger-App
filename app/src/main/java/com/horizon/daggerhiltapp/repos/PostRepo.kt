package com.horizon.daggerhiltapp.repos

import com.horizon.daggerhiltapp.Services.FakeApiService
import javax.inject.Inject

class PostRepo @Inject constructor(
    private val api: FakeApiService
) {
    suspend fun getPost() = api.getPost()
}