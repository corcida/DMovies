package com.corcida.data.photo.source

import com.corcida.domain.Photo


interface PhotoLocalDataSource {
    suspend fun getPhotos(): List<Photo>
    suspend fun savePhotos(photos: List<Photo>)

    suspend fun deletePhotos()

    suspend fun isEmpty(): Boolean
}