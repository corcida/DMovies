package com.corcida.data.photo.source

import com.corcida.domain.Photo

interface PhotoRemoteDataSource {
    suspend fun getPhotos(): List<Photo>

    suspend fun savePhoto(fileName: String, filePath: String)
}