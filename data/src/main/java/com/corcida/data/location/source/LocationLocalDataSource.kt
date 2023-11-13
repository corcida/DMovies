package com.corcida.data.location.source

import com.corcida.domain.Location

interface LocationLocalDataSource {

    suspend fun findCurrentLocation(): Location

    suspend fun getLastLocations(): List<Location>

    suspend fun saveLocations(locations: List<Location>)

    suspend fun deleteLocationsFromDatabase()

    suspend fun isEmpty(): Boolean

}