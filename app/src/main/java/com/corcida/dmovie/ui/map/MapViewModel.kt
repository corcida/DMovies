package com.corcida.dmovie.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.corcida.dmovie.framework.mappers.toUiModelLocation
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.dmovie.ui.movies.MoviesUiModel
import com.corcida.domain.MovieType
import com.corcida.usecases.location.GetLastLocationsUseCase
import com.corcida.usecases.movie.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLastLocationsUseCase: GetLastLocationsUseCase
): ScopeViewModel() {

    private val _model = MutableLiveData<MapUiModel>()
    val model: LiveData<MapUiModel> get() = _model

    private var locations = mutableListOf<LocationUIModel>()
    fun getData(){
        getLocationData()
    }

    private fun getLocationData() = launch {
        getLastLocationsUseCase.execute().onEach { dataState ->
            if (dataState.loading) _model.value = MapUiModel.Loading

            dataState.data?.let {
                val selectedIndex = getLocationSelected()
                locations = it.mapIndexed { index, location ->
                    location.toUiModelLocation(index, index == selectedIndex) }
                    .toMutableList()
                _model.value = MapUiModel.Content(locations)
            }

            dataState.error?.let {
                _model.value = MapUiModel.Error(it)
            }
        }.launchIn(this)
    }

    fun selectALocation(location : LocationUIModel){
        locations.onEach { it.selected = it == location }
        _model.value = MapUiModel.Content(locations)
    }

    private fun getLocationSelected() : Int{
        return locations.firstOrNull { it.selected }?.id ?: 0
    }

}