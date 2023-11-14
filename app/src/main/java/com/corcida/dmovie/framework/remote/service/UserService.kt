package com.corcida.dmovie.framework.remote.service

import com.corcida.dmovie.framework.remote.model.UserResponse
import com.corcida.dmovie.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("person/popular")
    suspend fun  getUsers(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = Constants.apiKey
    ) : UserResponse
}