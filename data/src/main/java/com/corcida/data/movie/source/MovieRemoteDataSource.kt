package com.corcida.data.movie.source

import com.corcida.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
}