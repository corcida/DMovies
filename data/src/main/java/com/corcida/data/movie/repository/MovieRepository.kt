package com.corcida.data.movie.repository

import com.corcida.data.movie.source.MovieLocalDataSource
import com.corcida.data.movie.source.MovieRemoteDataSource
import com.corcida.domain.Movie
import com.corcida.domain.MovieType

class MovieRepository (
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
){
    suspend fun isDatabaseEmpty(type: MovieType) = localDataSource.isEmpty(type.value)

    suspend fun saveMovies(movies: List<Movie>) = localDataSource.saveMovies(movies)

    suspend fun getMoviesFromDatabase(type: MovieType) = localDataSource.getMovies(type.value)

    suspend fun getMoviesFromServer(type: MovieType): List<Movie>{
        return when (type){
            MovieType.TOP_RATED -> remoteDataSource.getTopRatedMovies()
            MovieType.POPULAR -> remoteDataSource.getPopularMovies()
            MovieType.UPCOMING -> remoteDataSource.getUpcomingMovies()
        }
    }
}