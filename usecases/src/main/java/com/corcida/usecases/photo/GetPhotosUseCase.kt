package com.corcida.usecases.photo

import com.corcida.data.photo.repository.PhotoRepository
import com.corcida.domain.Photo
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPhotosUseCase (
    private val repository: PhotoRepository
) {

    fun execute (
        isDataRestored : Boolean
    ) : Flow<DataState<List<Photo>>> = flow {
        try {
            if (isDataRestored && !repository.isDatabaseEmpty())
                emit(DataState.success(repository.getPhotosFromDatabase()))
            else {
                emit(DataState.loading())
                try {
                    val photos = repository.getPhotosFromServer()
                    repository.deleteLocationsFromDatabase()
                    repository.savePhotosInDatabase(photos)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                emit(DataState.success(repository.getPhotosFromDatabase()))
            }

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}