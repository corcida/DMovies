package com.corcida.dmovie.framework.remote.implementation

import com.corcida.data.movie.source.MovieRemoteDataSource
import com.corcida.dmovie.framework.mappers.toDomainMovie
import com.corcida.dmovie.framework.remote.service.MovieService
import com.corcida.domain.Movie
import com.corcida.domain.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRemoteDataSourceImpl(
    private val movieService: MovieService
) : MovieRemoteDataSource {
    override suspend fun getPopularMovies(): List<Movie> = withContext(Dispatchers.IO){
        movieService.getPopularMovies(1)
            .results.map { it.toDomainMovie(MovieType.POPULAR.value) }
    }

    override suspend fun getTopRatedMovies(): List<Movie> = withContext(Dispatchers.IO){
        movieService.getTopRatedMovies(1)
            .results.map { it.toDomainMovie(MovieType.TOP_RATED.value) }
    }

    override suspend fun getUpcomingMovies(): List<Movie> = withContext(Dispatchers.IO){
        movieService.getUpcomingMovies(1)
            .results.map { it.toDomainMovie(MovieType.UPCOMING.value) }
    }
}