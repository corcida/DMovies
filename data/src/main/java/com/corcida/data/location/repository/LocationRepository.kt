package com.corcida.data.location.repository

import com.corcida.data.location.source.LocationLocalDataSource
import com.corcida.data.location.source.LocationRemoteDataSource
import com.corcida.data.location.util.PermissionChecker
import com.corcida.domain.Location

class LocationRepository(
    private val localDataSource: LocationLocalDataSource,
    private val remoteDataSource: LocationRemoteDataSource,
    private val permissionChecker: PermissionChecker
) {

    suspend fun findCurrentLocation(): Location? {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            localDataSource.findCurrentLocation()
        } else
            null
    }
    suspend fun isDatabaseEmpty() = localDataSource.isEmpty()
    suspend fun deleteLocationsFromDatabase() = localDataSource.deleteLocationsFromDatabase()

    suspend fun saveLocationsInDatabase(locations: List<Location>) {
        localDataSource.saveLocations(locations)
    }

    suspend fun saveLocationServer(location: Location) {
        remoteDataSource.saveLocation(location)
    }

    suspend fun getLocationsFromDatabase() : List<Location> {
        return localDataSource.getLastLocations()
    }

    suspend fun getLocationsFromServer() : List<Location> {
        return remoteDataSource.getLastLocations()
    }

}