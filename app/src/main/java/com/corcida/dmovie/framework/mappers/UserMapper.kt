package com.corcida.dmovie.framework.mappers

import com.corcida.dmovie.framework.local.entity.UserEntity
import com.corcida.domain.Movie
import com.corcida.domain.User
import com.corcida.dmovie.framework.remote.model.User as ServiceUser

fun User.toRoomUser() : UserEntity =
    UserEntity(id, name, movies, image, popularity)

fun UserEntity.toDomainUser() : User =
    User(id, name, movies, image, popularity)

fun ServiceUser.toDomainUser() : User {
    return User(id,
        name,
        if (movies.isNotEmpty())
            movies.map { Movie(it.id, it.title?:it.name?:"Título", it.image, "") }
        else listOf(),
        image,
        popularity)
}



