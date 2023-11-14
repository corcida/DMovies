package com.corcida.dmovie.ui.map

data class LocationModel(
    val id: Int,
    var selected: Boolean,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String
)
