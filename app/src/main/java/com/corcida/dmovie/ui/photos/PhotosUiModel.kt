package com.corcida.dmovie.ui.photos

sealed class PhotosUiModel {
    data object Loading : PhotosUiModel()
    class Error(val error: String) : PhotosUiModel()
    class Content(val photos: List<PhotoModel?>) : PhotosUiModel()
}