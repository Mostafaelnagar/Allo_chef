package app.te.alo_chef.core.di.module

import android.content.Context
import app.te.alo_chef.core.notifications.handler.NotificationHandler
import app.te.alo_chef.data.local.preferences.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppPreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context) = AppPreferences(context)

    @Provides
    @Singleton
    fun providePushNotificationHandler(@ApplicationContext context: Context) = NotificationHandler(context)

}