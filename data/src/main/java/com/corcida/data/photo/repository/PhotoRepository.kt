package com.corcida.data.photo.repository

import com.corcida.data.photo.source.PhotoLocalDataSource
import com.corcida.data.photo.source.PhotoRemoteDataSource
import com.corcida.domain.Photo
import java.net.URI

class PhotoRepository (
    private val localDataSource: PhotoLocalDataSource,
    private val remoteDataSource: PhotoRemoteDataSource
){
    suspend fun isDatabaseEmpty() = localDataSource.isEmpty()

    suspend fun deleteLocationsFromDatabase() = localDataSource.deletePhotos()

    suspend fun savePhotosInDatabase(photos: List<Photo>) {
        localDataSource.savePhotos(photos)
    }

    suspend fun savePhotonServer(fileName: String, filePath: String) {
        remoteDataSource.savePhoto(fileName, filePath)
    }

    suspend fun getPhotosFromDatabase() : List<Photo> {
        return localDataSource.getPhotos()
    }

    suspend fun getPhotosFromServer() : List<Photo> {
        return remoteDataSource.getPhotos()
    }
}