package com.corcida.dmovie.framework.local.implementation

import com.corcida.data.user.source.UserLocalDataSource
import com.corcida.dmovie.framework.local.dao.UserDao
import com.corcida.dmovie.framework.mappers.toDomainUser
import com.corcida.dmovie.framework.mappers.toRoomUser
import com.corcida.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource{
    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        userDao.userCount() <= 0
    }

    override suspend fun saveUser(user: User) = withContext(Dispatchers.IO) {
        userDao.insertUser(user.toRoomUser())
    }

    override suspend fun getUser(): User = withContext(Dispatchers.IO) {
        userDao.findUser().toDomainUser()
    }

    override suspend fun deleteUser() = withContext(Dispatchers.IO) {
        userDao.deleteAll()
    }
}