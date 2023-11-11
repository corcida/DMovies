package com.corcida.dmovie.framework.local.entity

import androidx.room.Entity

@Entity(
    tableName = "movie",
    primaryKeys = ["id", "type"]
)
data class MovieEntity (
    val id: Int,
    val title: String,
    val image: String,
    val type: String
)