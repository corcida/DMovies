package com.corcida.dmovie.framework.remote.service

import com.corcida.dmovie.framework.remote.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/top_rated")
    suspend fun  getTopRatedMovies(@Query("page") page: Int = 1) : MovieResponse

    @GET("movie/popular")
    suspend fun  getPopularMovies(@Query("page") page: Int = 1) : MovieResponse

    @GET("movie/upcoming")
    suspend fun  getUpcomingMovies(@Query("page") page: Int = 1) : MovieResponse

}