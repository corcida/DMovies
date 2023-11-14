package com.corcida.dmovie.ui.map

sealed class MapUiModel {
    data object Loading : MapUiModel()
    class Error( val error: String) : MapUiModel()
    class Content(val locations: List<LocationModel>) : MapUiModel()

}
