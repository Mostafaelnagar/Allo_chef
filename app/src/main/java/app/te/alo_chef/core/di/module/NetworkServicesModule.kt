package app.te.alo_chef.core.di.module

import app.te.alo_chef.data.account.data_source.remote.AccountServices
import app.te.alo_chef.data.auth.data_source.remote.AuthServices
import app.te.alo_chef.data.general.data_source.remote.GeneralServices
import app.te.alo_chef.data.home.data_source.remote.HomeServices
import app.te.alo_chef.data.intro.data_source.IntroServices
import app.te.alo_chef.data.meal_details.data_source.MealDetailsServices
import app.te.alo_chef.data.my_orders.data_source.OrdersServices
import app.te.alo_chef.data.profile.data_source.ProfileServices
import app.te.alo_chef.data.settings.data_source.remote.SettingsServices
import app.te.alo_chef.data.subscriptions.data_source.SubscriptionsServices
import app.te.alo_chef.data.wallet.data_source.WalletServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServicesModule {

    @Provides
    @Singleton
    fun provideAuthServices(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)

    @Provides
    @Singleton
    fun provideProfileServices(retrofit: Retrofit): ProfileServices =
        retrofit.create(ProfileServices::class.java)

    @Provides
    @Singleton
    fun provideAccountServices(retrofit: Retrofit): AccountServices =
        retrofit.create(AccountServices::class.java)

    @Provides
    @Singleton
    fun provideGeneralServices(retrofit: Retrofit): GeneralServices =
        retrofit.create(GeneralServices::class.java)


    @Provides
    @Singleton
    fun provideHomeServices(retrofit: Retrofit): HomeServices =
        retrofit.create(HomeServices::class.java)

    @Provides
    @Singleton
    fun provideIntroServices(retrofit: Retrofit): IntroServices =
        retrofit.create(IntroServices::class.java)

    @Provides
    @Singleton
    fun provideSettingsServices(retrofit: Retrofit): SettingsServices =
        retrofit.create(SettingsServices::class.java)

    @Provides
    @Singleton
    fun provideMealDetailsServices(retrofit: Retrofit): MealDetailsServices =
        retrofit.create(MealDetailsServices::class.java)

    @Provides
    @Singleton
    fun provideWalletServices(retrofit: Retrofit): WalletServices =
        retrofit.create(WalletServices::class.java)

    @Provides
    @Singleton
    fun provideOrdersServices(retrofit: Retrofit): OrdersServices =
        retrofit.create(OrdersServices::class.java)

    @Provides
    @Singleton
    fun provideSubscriptionsServices(retrofit: Retrofit): SubscriptionsServices =
        retrofit.create(SubscriptionsServices::class.java)

}