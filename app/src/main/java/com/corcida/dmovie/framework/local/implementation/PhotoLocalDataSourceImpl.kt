package com.corcida.dmovie.framework.local.implementation

import com.corcida.data.photo.source.PhotoLocalDataSource
import com.corcida.dmovie.framework.local.dao.PhotoDao
import com.corcida.dmovie.framework.mappers.toDomainPhoto
import com.corcida.dmovie.framework.mappers.toRoomPhoto
import com.corcida.domain.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoLocalDataSourceImpl (
    private val photoDao: PhotoDao
): PhotoLocalDataSource {
    override suspend fun getPhotos(): List<Photo> = withContext(Dispatchers.IO) {
        photoDao.getPhotos().map { it.toDomainPhoto() }
    }

    override suspend fun savePhotos(photos: List<Photo>) = withContext(Dispatchers.IO) {
        photoDao.insertPhotos(photos.map { it.toRoomPhoto() })
    }

    override suspend fun deletePhotos() = withContext(Dispatchers.IO) {
        photoDao.deleteAll()
    }

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        photoDao.photoCount() <= 0
    }
}