package com.corcida.dmovie.di

import android.app.Application
import androidx.room.Room
import com.corcida.data.movie.source.MovieLocalDataSource
import com.corcida.data.user.source.UserLocalDataSource
import com.corcida.dmovie.framework.local.Database
import com.corcida.dmovie.framework.local.dao.MovieDao
import com.corcida.dmovie.framework.local.dao.UserDao
import com.corcida.dmovie.framework.local.implementation.MovieLocalDataSourceImpl
import com.corcida.dmovie.framework.local.implementation.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application): Database = Room.databaseBuilder(
        app,
        Database::class.java,
        "movie-db"
    ).build()

    @Provides
    fun movieDaoProvider(db: Database): MovieDao = db.movieDao()

    @Provides
    fun userDaoProvider(db: Database): UserDao = db.userDao()

    @Provides
    fun movieLocalDataSourceProvider(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao)

    @Provides
    fun userLocalDataSourceProvider(userDao: UserDao): UserLocalDataSource =
        UserLocalDataSourceImpl(userDao)

}