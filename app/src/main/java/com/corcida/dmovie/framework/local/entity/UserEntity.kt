package com.corcida.dmovie.framework.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.corcida.domain.Movie

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val movies: List<Movie>,
    val image: String,
    val popularity: Float
)