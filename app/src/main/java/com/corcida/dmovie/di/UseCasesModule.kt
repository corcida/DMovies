package com.corcida.dmovie.di

import com.corcida.data.location.repository.LocationRepository
import com.corcida.data.movie.repository.MovieRepository
import com.corcida.data.photo.repository.PhotoRepository
import com.corcida.data.user.repository.UserRepository
import com.corcida.usecases.location.FindLastLocationUseCase
import com.corcida.usecases.location.GetLastLocationsUseCase
import com.corcida.usecases.movie.GetMoviesUseCase
import com.corcida.usecases.photo.GetPhotosUseCase
import com.corcida.usecases.photo.SavePhotoUseCase
import com.corcida.usecases.user.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    fun getMoviesUseCaseProvider(
        repository: MovieRepository
    ) = GetMoviesUseCase(repository)

    @Provides
    fun getUserUseCaseProvider(
        repository: UserRepository
    ) = GetUserUseCase(repository)

    @Provides
    fun findLastLocationUseCaseProvider(
        repository: LocationRepository
    ) = FindLastLocationUseCase(repository)

    @Provides
    fun getPhotosUseCaseProvider(
        repository: PhotoRepository
    ) = GetPhotosUseCase(repository)

    @Provides
    fun savePhotoUseCaseProvider(
        repository: PhotoRepository
    ) = SavePhotoUseCase(repository)
}