package com.corcida.domain

data class User(
    val id: Int,
    val name: String,
    val movies: List<Movie>,
    val image: String,
    val popularity: Float
)

