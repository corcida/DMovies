package com.corcida.dmovie.framework.mappers

import com.corcida.dmovie.framework.local.entity.UserEntity
import com.corcida.domain.Movie
import com.corcida.domain.User
import com.corcida.dmovie.framework.remote.model.User as ServiceUser

fun User.toRoomUser() : UserEntity =
    UserEntity(id, name, movies, image, popularity)

fun UserEntity.toDomainUser() : User =
    User(id, name, movies, image, popularity)

fun ServiceUser.toDomainUser() : User =
    User(id, name, movies.map { Movie(it.id, it.title, it.image, "") }, image, popularity)


