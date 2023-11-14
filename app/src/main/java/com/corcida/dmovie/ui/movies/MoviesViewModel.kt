package com.corcida.dmovie.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.domain.MovieType
import com.corcida.usecases.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ScopeViewModel() {

    private val _model = MutableLiveData<MoviesUiModel>()
    val model: LiveData<MoviesUiModel> get() = _model

    fun refreshData(types: List<MovieType>){
        types.forEach { getMoviesData(it, false) }
    }

    fun getData(types: List<MovieType>){
        if (types.size != MovieType.entries.size){
            types.forEach { getMoviesData(it, true) }
        } else{
            MovieType.entries.forEach { getMoviesData(it,false) }
        }
    }

    private fun getMoviesData(type: MovieType, isDataRestored: Boolean) = launch {
         getMoviesUseCase.execute(type, isDataRestored).onEach { dataState ->
             if (dataState.loading) _model.value = MoviesUiModel.Loading(type)

             dataState.data?.let {
                 _model.value = MoviesUiModel.Content(type, it)
             }

             dataState.error?.let {
                 _model.value = MoviesUiModel.Error(type, it)
             }
         }.launchIn(this)
    }

}