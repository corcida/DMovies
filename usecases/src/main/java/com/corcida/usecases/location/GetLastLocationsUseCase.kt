package com.corcida.usecases.location

import com.corcida.data.location.repository.LocationRepository
import com.corcida.domain.Location
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLastLocationsUseCase (
    private val repository: LocationRepository
) {

    fun execute(): Flow<DataState<List<Location>>> = flow {
        try {
            emit(DataState.loading())
            try {
                val locations = repository.getLocationsFromServer()
                repository.deleteLocationsFromDatabase()
                repository.saveLocationsInDatabase(locations)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            emit(DataState.success(repository.getLocationsFromDatabase()))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }

}