package com.corcida.dmovie.di

import com.corcida.data.location.repository.LocationRepository
import com.corcida.data.location.source.LocationLocalDataSource
import com.corcida.data.location.source.LocationRemoteDataSource
import com.corcida.data.location.util.PermissionChecker
import com.corcida.data.movie.repository.MovieRepository
import com.corcida.data.movie.source.MovieLocalDataSource
import com.corcida.data.movie.source.MovieRemoteDataSource
import com.corcida.data.photo.repository.PhotoRepository
import com.corcida.data.photo.source.PhotoLocalDataSource
import com.corcida.data.photo.source.PhotoRemoteDataSource
import com.corcida.data.user.repository.UserRepository
import com.corcida.data.user.source.UserLocalDataSource
import com.corcida.data.user.source.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun moviesRepositoryProvider(
        localDataSource: MovieLocalDataSource,
        remoteDataSource: MovieRemoteDataSource
    ) = MovieRepository(localDataSource, remoteDataSource)

    @Provides
    fun userRepositoryProvider(
        localDataSource: UserLocalDataSource,
        remoteDataSource: UserRemoteDataSource
    ) = UserRepository(localDataSource, remoteDataSource)

    @Provides
    fun locationRepositoryProvider(
        localDataSource: LocationLocalDataSource,
        remoteDataSource: LocationRemoteDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(localDataSource, remoteDataSource, permissionChecker)

    @Provides
    fun photonRepositoryProvider(
        localDataSource: PhotoLocalDataSource,
        remoteDataSource: PhotoRemoteDataSource
    ) = PhotoRepository(localDataSource, remoteDataSource)
}