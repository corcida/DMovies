package com.corcida.dmovie.framework.mappers

import com.corcida.dmovie.framework.local.entity.MovieEntity
import com.corcida.domain.Movie
import com.corcida.dmovie.framework.remote.model.Movie as ServiceMovie

fun Movie.toRoomMovie() : MovieEntity =
    MovieEntity(id, title, image, type)

fun MovieEntity.toDomainMovie(): Movie =
    Movie(id, title, image, type)

fun ServiceMovie.toDomainMovie(type: String): Movie =
    Movie(id, title, image, type)

