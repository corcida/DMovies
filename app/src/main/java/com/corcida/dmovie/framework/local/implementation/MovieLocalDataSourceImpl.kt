package com.corcida.dmovie.framework.local.implementation

import com.corcida.data.movie.source.MovieLocalDataSource
import com.corcida.dmovie.framework.local.dao.MovieDao
import com.corcida.dmovie.framework.mappers.toDomainMovie
import com.corcida.dmovie.framework.mappers.toRoomMovie
import com.corcida.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieLocalDataSourceImpl (
    private val movieDao: MovieDao
): MovieLocalDataSource {
    override suspend fun isEmpty(type: String): Boolean = withContext(Dispatchers.IO) {
        movieDao.moviesCount(type) <= 0
    }

    override suspend fun saveMovies(movies: List<Movie>) = withContext(Dispatchers.IO) {
        movieDao.insertMovies(movies.map { it.toRoomMovie() })
    }

    override suspend fun getMovies(type: String): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.findMovies(type).map { it.toDomainMovie() }
    }
}