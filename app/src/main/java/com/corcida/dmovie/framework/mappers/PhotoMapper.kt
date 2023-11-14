package com.corcida.dmovie.framework.mappers

import com.corcida.dmovie.framework.local.entity.PhotoEntity
import com.corcida.dmovie.ui.photos.PhotoModel
import com.corcida.domain.Photo


fun Photo.toRoomPhoto() : PhotoEntity =
    PhotoEntity(0, path)

fun Photo.toUiModelPhoto() : PhotoModel =
    PhotoModel(0, path)

fun PhotoEntity.toDomainPhoto(): Photo =
    Photo(path)