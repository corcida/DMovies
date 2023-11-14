package com.corcida.dmovie.di

import android.app.Application
import androidx.core.app.NotificationCompat
import com.corcida.dmovie.R
import com.corcida.dmovie.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun notificationCompatBuilderProvider(app: Application) : NotificationCompat.Builder =
        NotificationCompat.Builder(app, Constants.channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Localización")
            .setContentText("¡Nueva localización añadida, observala en el mapa!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

}