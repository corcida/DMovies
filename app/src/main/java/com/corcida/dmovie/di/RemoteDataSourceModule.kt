package com.corcida.dmovie.di

import android.os.Build
import com.corcida.data.location.source.LocationRemoteDataSource
import com.corcida.data.movie.source.MovieRemoteDataSource
import com.corcida.data.photo.source.PhotoRemoteDataSource
import com.corcida.data.user.source.UserRemoteDataSource
import com.corcida.dmovie.framework.remote.implementation.LocationRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.implementation.MovieRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.implementation.PhotoRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.implementation.UserRemoteDataSourceImpl
import com.corcida.dmovie.framework.remote.service.MovieService
import com.corcida.dmovie.framework.remote.service.UserService
import com.corcida.dmovie.util.Constants
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
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
    fun pseudoUniqueDeviceIdProvider() : String = Build.DEVICE
    @Singleton
    @Provides
    fun firebaseFireStoreProvider() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun firebaseStorageProvider() = Firebase.storage

    @Singleton
    @Provides
    fun firebaseStorageReferenceProvider(storage: FirebaseStorage, deviceId: String) : StorageReference =
        storage.reference.child("photos").child(deviceId)

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

    @Provides
    fun locationRemoteDatasourceProvider(
        firebaseFirestore: FirebaseFirestore,
        deviceId : String
    ) : LocationRemoteDataSource = LocationRemoteDataSourceImpl(firebaseFirestore, deviceId)

    @Provides
    fun photoRemoteDatasourceProvider(
        reference: StorageReference
    ) : PhotoRemoteDataSource = PhotoRemoteDataSourceImpl(reference)
}