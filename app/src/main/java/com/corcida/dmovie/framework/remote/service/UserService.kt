package com.corcida.dmovie.framework.remote.service

import com.corcida.dmovie.framework.remote.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("person/popular")
    suspend fun  getUsers(@Query("page") page: Int = 1) : UserResponse
}