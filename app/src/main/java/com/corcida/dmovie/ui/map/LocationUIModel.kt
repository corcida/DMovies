package com.corcida.dmovie.ui.map

data class LocationUIModel(
    val id: Int,
    var selected: Boolean,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val date: String
)
