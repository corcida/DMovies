package com.corcida.data.user.source

import com.corcida.domain.User


interface UserRemoteDataSource {
    suspend fun getUser(): User
}