package com.corcida.usecases.user

import com.corcida.data.user.repository.UserRepository
import com.corcida.domain.User
import com.corcida.domain.data.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase (
    private val repository: UserRepository
) {

    fun execute() : Flow<DataState<User>> = flow {
        try {
            emit(DataState.loading())
            try {
                val user = repository.getUserFromServer()
                if (!repository.isDatabaseEmpty() && user.id != repository.getUserFromDatabase().id)
                    repository.deleteUserFromDatabase()
                repository.saveUser(user)
            } catch (e: Exception){
                e.printStackTrace()
            }

            println("user ${repository.getUserFromDatabase()}")
            println("count ${repository.isDatabaseEmpty()}")

            if (repository.isDatabaseEmpty())
                emit(DataState.error("There is no data"))
            else
                emit(DataState.success(repository.getUserFromDatabase()))

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }

}