package com.corcida.data.movie.source

import com.corcida.domain.Movie

interface MovieLocalDataSource {
    suspend fun isEmpty(type: String): Boolean
    suspend fun saveMovies(movies: List<Movie>)
    suspend fun getMovies(type: String): List<Movie>

}