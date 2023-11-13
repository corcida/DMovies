package com.corcida.usecases.location

import com.corcida.data.location.repository.LocationRepository
import com.corcida.domain.Location
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FindLastLocationUseCase  (
    private val repository: LocationRepository
) {
    fun execute(): Flow<DataState<Location>> = flow {
        try {
            emit(DataState.loading())
            val location = repository.findCurrentLocation()
            location?.let {
                repository.saveLocationServer(it)
                emit(DataState.success(it))
            }?:run {
                emit(DataState.error("Could not retrieve current location from user"))
            }
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }

}