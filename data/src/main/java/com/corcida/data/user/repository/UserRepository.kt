package com.corcida.data.user.repository

import com.corcida.data.user.source.UserLocalDataSource
import com.corcida.data.user.source.UserRemoteDataSource
import com.corcida.domain.User

class UserRepository (
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
){
    suspend fun isDatabaseEmpty() = localDataSource.isEmpty()

    suspend fun saveUser(user: User) = localDataSource.saveUser(user)
    suspend fun deleteUserFromDatabase() = localDataSource.deleteUser()

    suspend fun getUserFromDatabase() = localDataSource.getUser()

    suspend fun getUserFromServer() = remoteDataSource.getUser()

}