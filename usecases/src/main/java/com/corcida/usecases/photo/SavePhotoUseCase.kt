package com.corcida.usecases.photo

import com.corcida.data.photo.repository.PhotoRepository
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.URI

class SavePhotoUseCase (
    private val repository: PhotoRepository
) {
    fun execute (
        fileName: String,
        filePath: URI
    ) : Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading())
            try {
                repository.savePhotonServer(fileName, filePath)
                emit(DataState.success(true))
            } catch (e: Exception) {
                emit(DataState.error(e.message ?: "Unknown Error"))
                e.printStackTrace()
            }

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}