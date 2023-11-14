package com.corcida.dmovie.framework.remote.implementation

import android.net.Uri
import com.corcida.data.photo.source.PhotoRemoteDataSource
import com.corcida.domain.Photo
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI

class PhotoRemoteDataSourceImpl (
    private val reference : StorageReference
): PhotoRemoteDataSource {
    override suspend fun getPhotos(): List<Photo> = withContext(Dispatchers.IO) {
        val photos = mutableListOf<Photo>()
        val listResult: ListResult = reference.listAll().await()
        for (item in listResult.items) {
            val url = item.downloadUrl.await().toString()
            photos.add(Photo(url))
        }
        photos
    }
    override suspend fun savePhoto(fileName: String, filePath: URI): Unit = withContext(Dispatchers.IO) {
        val fileRef = reference.child(fileName)
        val uploadTask = fileRef.putFile(Uri.parse(filePath.toString()))
        uploadTask.await()
    }

}