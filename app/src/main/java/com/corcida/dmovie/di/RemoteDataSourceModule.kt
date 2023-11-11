package com.corcida.dmovie.di

import com.corcida.data.movie.source.MovieRemoteDataSource
import com.corcida.data.user.source.UserRemoteDataSource
import com.corcida.dmovie.framework.remote.implementation.MovieRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.implementation.UserRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.service.MovieService
import com.corcida.dmovie.framework.remote.service.UserService
import com.corcida.dmovie.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun clientProvider(): OkHttpClient =   OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer " + Constants.token)
            val request = requestBuilder.build()
            chain.proceed(request)
        }.addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun retrofitProvider(client : OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun movieServiceProvider(retrofit: Retrofit) : MovieService  =
        retrofit.create(MovieService::class.java)

    @Singleton
    @Provides
    fun userServiceProvider(retrofit: Retrofit) : UserService  =
        retrofit.create(UserService::class.java)

    @Provides
    fun movieRemoteDataSourceProvider(movieService: MovieService) : MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(movieService)

    @Provides
    fun userRemoteDataSourceProvider(userService: UserService) : UserRemoteDataSource =
        UserRemoteDataSourceImpl(userService)
}