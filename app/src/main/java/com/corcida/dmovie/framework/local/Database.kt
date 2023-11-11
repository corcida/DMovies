package com.corcida.dmovie.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.corcida.dmovie.framework.local.dao.MovieDao
import com.corcida.dmovie.framework.local.dao.UserDao
import com.corcida.dmovie.framework.local.entity.MovieEntity
import com.corcida.dmovie.framework.local.entity.UserEntity

@Database(entities = [MovieEntity::class, UserEntity::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
}