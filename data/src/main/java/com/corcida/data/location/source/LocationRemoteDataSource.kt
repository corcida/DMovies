package com.corcida.data.location.source

import com.corcida.domain.Location

interface LocationRemoteDataSource {

    suspend fun getLastLocations(): List<Location>

    suspend fun saveLocation(location: Location)

}