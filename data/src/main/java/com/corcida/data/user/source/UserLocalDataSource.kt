package com.corcida.data.user.source

import com.corcida.domain.User

interface UserLocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun saveUser(user: User)
    suspend fun getUser(): User
    suspend fun deleteUser()
}