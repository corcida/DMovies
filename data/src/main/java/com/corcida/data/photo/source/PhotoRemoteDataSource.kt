package com.corcida.data.photo.source

import com.corcida.domain.Photo
import java.net.URI

interface PhotoRemoteDataSource {
    suspend fun getPhotos(): List<Photo>

    suspend fun savePhoto(fileName: String, filePath: URI)
}