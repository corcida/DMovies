package com.corcida.dmovie.framework.local.implementation

import com.corcida.data.photo.source.PhotoLocalDataSource
import com.corcida.dmovie.framework.local.dao.PhotoDao
import com.corcida.domain.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoLocalDataSourceImpl (
    private val photoDao: PhotoDao
): PhotoLocalDataSource {
    override suspend fun getPhotos(): List<Photo> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun savePhotos(locations: List<Photo>) = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePhotos() = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }
}