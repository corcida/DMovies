package com.corcida.dmovie.framework.remote.implementation

import com.corcida.data.user.source.UserRemoteDataSource
import com.corcida.dmovie.framework.mappers.toDomainUser
import com.corcida.dmovie.framework.remote.service.UserService
import com.corcida.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRemoteDataSourceImpl (
    private val userService: UserService
): UserRemoteDataSource {
    override suspend fun getUser(): User = withContext(Dispatchers.IO){
        userService.getUsers(1)
            .results.first().toDomainUser()
    }
}