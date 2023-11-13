package com.corcida.dmovie.di

import android.app.Application
import android.location.Geocoder
import androidx.room.Room
import com.corcida.data.location.source.LocationLocalDataSource
import com.corcida.data.location.util.PermissionChecker
import com.corcida.data.movie.source.MovieLocalDataSource
import com.corcida.data.photo.source.PhotoLocalDataSource
import com.corcida.data.user.source.UserLocalDataSource
import com.corcida.dmovie.framework.local.Database
import com.corcida.dmovie.framework.local.dao.LocationDao
import com.corcida.dmovie.framework.local.dao.MovieDao
import com.corcida.dmovie.framework.local.dao.PhotoDao
import com.corcida.dmovie.framework.local.dao.UserDao
import com.corcida.dmovie.framework.local.implementation.LocationLocalDataSourceImpl
import com.corcida.dmovie.framework.local.implementation.MovieLocalDataSourceImpl
import com.corcida.dmovie.framework.local.implementation.PermissionCheckerImpl
import com.corcida.dmovie.framework.local.implementation.PhotoLocalDataSourceImpl
import com.corcida.dmovie.framework.local.implementation.UserLocalDataSourceImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    @Singleton
    fun geocoderProvider(app: Application) : Geocoder = Geocoder(app)

    @Provides
    @Singleton
    fun fusedLocationClientProvider(app: Application) : FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)

    @Singleton
    @Provides
    fun permissionCheckerProvider(
        app: Application
    ): PermissionChecker =
        PermissionCheckerImpl(app)

    @Provides
    fun movieDaoProvider(db: Database): MovieDao = db.movieDao()

    @Provides
    fun userDaoProvider(db: Database): UserDao = db.userDao()

    @Provides
    fun locationDaoProvider(db: Database): LocationDao = db.locationDao()

    @Provides
    fun photonDaoProvider(db: Database): PhotoDao = db.photoDao()

    @Provides
    fun movieLocalDataSourceProvider(movieDao: MovieDao): MovieLocalDataSource =
        MovieLocalDataSourceImpl(movieDao)

    @Provides
    fun userLocalDataSourceProvider(userDao: UserDao): UserLocalDataSource =
        UserLocalDataSourceImpl(userDao)

    @Provides
    fun locationLocalDataSourceProvider(
        geocoder: Geocoder,
        fusedLocationClient: FusedLocationProviderClient,
        locationDao: LocationDao
    ): LocationLocalDataSource =
        LocationLocalDataSourceImpl(geocoder, fusedLocationClient, locationDao)

    @Provides
    fun photoLocalDataSourceProvider(
        photoDao: PhotoDao
    ): PhotoLocalDataSource =
        PhotoLocalDataSourceImpl(photoDao)

}