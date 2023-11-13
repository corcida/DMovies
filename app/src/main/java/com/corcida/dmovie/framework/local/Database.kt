package com.corcida.dmovie.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.corcida.dmovie.framework.local.dao.LocationDao
import com.corcida.dmovie.framework.local.dao.MovieDao
import com.corcida.dmovie.framework.local.dao.PhotoDao
import com.corcida.dmovie.framework.local.dao.UserDao
import com.corcida.dmovie.framework.local.entity.LocationEntity
import com.corcida.dmovie.framework.local.entity.MovieEntity
import com.corcida.dmovie.framework.local.entity.PhotoEntity
import com.corcida.dmovie.framework.local.entity.UserEntity

@Database(entities = [MovieEntity::class, UserEntity::class, LocationEntity::class, PhotoEntity::class], version = 1)
abstract class Database : RoomDatabase(){

    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
    abstract fun locationDao(): LocationDao
    abstract fun photoDao(): PhotoDao
}