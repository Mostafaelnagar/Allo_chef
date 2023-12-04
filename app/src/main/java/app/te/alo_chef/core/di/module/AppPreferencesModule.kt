package app.te.alo_chef.core.di.module

import android.content.Context
import app.te.alo_chef.core.notifications.handler.NotificationHandler
import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.domain.utils.validation.ValidatePhone
import app.te.alo_chef.presentation.auth.sign_up.RegisterUiState
import app.te.alo_chef.presentation.checkout.ui_state.CheckoutUiState
import app.te.alo_chef.presentation.my_locations.ui_state.AddLocationUiState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    fun provideAddLocationUiState(@ApplicationContext context: Context) =
        AddLocationUiState(context)

    @Provides
    @Singleton
    fun providePushNotificationHandler(@ApplicationContext context: Context) =
        NotificationHandler(context)

    @Provides
    @Singleton
    fun provideCheckoutState(@ApplicationContext context: Context) =
        CheckoutUiState(context)

    @Provides
    @Singleton
    fun provideRegisterState(@ApplicationContext context: Context) =
        RegisterUiState(validatePhone = ValidatePhone(context))

    @Provides
    @Singleton
    fun provideValidatePhone(@ApplicationContext context: Context) =
        ValidatePhone(context = context)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}