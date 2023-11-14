package com.corcida.dmovie.ui.photos

import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corcida.dmovie.framework.mappers.toUiModelLocation
import com.corcida.dmovie.framework.mappers.toUiModelPhoto
import com.corcida.dmovie.ui.common.ScopeViewModel
import com.corcida.usecases.photo.GetPhotosUseCase
import com.corcida.usecases.photo.SavePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotosUseCase,
    private val savePhotoUseCase: SavePhotoUseCase
): ScopeViewModel() {

    private val _model = MutableLiveData<PhotosUiModel>()
    val model: LiveData<PhotosUiModel> get() = _model

    private var photos = mutableListOf<PhotoModel?>()

    fun getData(){
        getPhotosData(photos.isEmpty())
    }

    fun refreshData(){
        getPhotosData(false)
    }

    fun saveNewFiles(files: List<Uri>){
        files.onEach {
            photos.add(null)
            savePhotos(randomString(10), URI.create(it.toString()))
        }
        _model.value = PhotosUiModel.Content(photos.map { it?.copy() })
    }

    private fun getPhotosData(isDataRestored: Boolean) = launch {
        getPhotoUseCase.execute(isDataRestored).onEach { dataState ->
             if (dataState.loading) _model.value = PhotosUiModel.Loading

             dataState.data?.let { photoList ->
                 photos = photoList.map { photo -> photo.toUiModelPhoto() }.toMutableList()
                 _model.value = PhotosUiModel.Content(photos.map { it?.copy() })
             }
             dataState.error?.let {
                 _model.value = PhotosUiModel.Error(it)
             }
         }.launchIn(this)
    }

    private fun savePhotos(fileName: String, filePath: URI) = launch {
        savePhotoUseCase.execute(fileName, filePath).onEach { dataState ->
            dataState.data?.let { success ->
               if (success)
                   getPhotosData(false)
            }

            dataState.error?.let {
                _model.value = PhotosUiModel.Error(it)
            }
        }.launchIn(this)
    }

    private fun randomString(length: Int): String =
        buildString {
            repeat(length) {
                append((0 until 36).random().toString(36))
            }
        }
}