package com.corcida.dmovie.framework.remote.implementation

import com.corcida.data.location.source.LocationRemoteDataSource
import com.corcida.dmovie.framework.mappers.toDomainLocation
import com.corcida.dmovie.framework.mappers.toServiceLocation
import com.corcida.domain.Location
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import com.corcida.dmovie.framework.remote.model.Location as ServiceLocation

class LocationRemoteDataSourceImpl (
    private val firestore: FirebaseFirestore,
    private val device : String
): LocationRemoteDataSource{
    override suspend fun getLastLocations(): List<Location> = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { continuation ->
            firestore.collection("locations")
                .whereEqualTo("device", device)
                .get()
                .addOnSuccessListener {documents ->
                    val locations = mutableListOf<Location>()
                    for (document in documents){
                        val serviceLocation = document.toObject(ServiceLocation::class.java)
                        locations.add(serviceLocation.toDomainLocation())
                    }
                    continuation.resume(locations)
                }
        }
    }

    override suspend fun saveLocation(location: Location) {
        firestore.collection("locations")
            .add(location.toServiceLocation(device))
            .await()
    }
}