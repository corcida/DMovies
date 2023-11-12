package com.corcida.dmovie.ui.movies

import com.corcida.domain.Movie
import com.corcida.domain.MovieType

sealed class MoviesUiModel {
    class Loading(val type: MovieType) : MoviesUiModel()
    class Error(val type: MovieType, val error: String) : MoviesUiModel()
    class Content(val type: MovieType, val movies: List<Movie>) : MoviesUiModel()
}