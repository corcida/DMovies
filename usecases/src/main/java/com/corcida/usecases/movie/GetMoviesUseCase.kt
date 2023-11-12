package com.corcida.usecases.movie

import com.corcida.data.movie.repository.MovieRepository
import com.corcida.domain.Movie
import com.corcida.domain.MovieType
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCase(
    private val repository: MovieRepository
) {
    fun execute(
        type: MovieType,
        isDataRestored: Boolean
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            if (isDataRestored && !repository.isDatabaseEmpty(type))
                emit(DataState.success(repository.getMoviesFromDatabase(type)))
            else {
                emit(DataState.loading())
                try {
                    val movies = repository.getRecipesFromServer(type)
                    repository.saveMovies(movies)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (repository.isDatabaseEmpty(type))
                    emit(DataState.error("There is no data"))
                else
                    emit(DataState.success(repository.getMoviesFromDatabase(type)))
            }

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }

}