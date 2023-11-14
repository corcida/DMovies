package com.corcida.dmovie.framework.local.implementation

import android.annotation.SuppressLint
import android.location.Geocoder
import com.corcida.data.location.source.LocationLocalDataSource
import com.corcida.dmovie.framework.local.dao.LocationDao
import com.corcida.dmovie.framework.mappers.toDomainLocation
import com.corcida.dmovie.framework.mappers.toRoomLocation
import com.corcida.domain.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class LocationLocalDataSourceImpl(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient,
    private val locationDao: LocationDao

) : LocationLocalDataSource {
    @SuppressLint("MissingPermission")
    override suspend fun findCurrentLocation(): Location = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    if (it.isSuccessful && it.result != null)
                        continuation.resume(it.result.toDomainLocation(geocoder))
                    else
                        continuation.cancel()
                }
        }
    }

    override suspend fun getLastLocations(): List<Location> = withContext(Dispatchers.IO) {
        locationDao.getLocations().map { it.toDomainLocation() }
    }

    override suspend fun saveLocations(locations: List<Location>) = withContext(Dispatchers.IO) {
        locationDao.insertLocations(locations.map { it.toRoomLocation() })
    }

    override suspend fun deleteLocationsFromDatabase() = withContext(Dispatchers.IO) {
        locationDao.deleteAll()
    }

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        locationDao.locationCount() <= 0
    }
}