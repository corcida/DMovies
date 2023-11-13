package com.corcida.dmovie.ui.main

sealed class MainUiModel {
    data object Notify : MainUiModel()
    class Error( val error: String) : MainUiModel()
    data object RequestLocationPermission : MainUiModel()

}
