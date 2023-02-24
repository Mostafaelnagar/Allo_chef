package app.te.alo_chef.core.di.module

import app.te.alo_chef.data.account.data_source.remote.AccountRemoteDataSource
import app.te.alo_chef.data.account.repository.AccountRepositoryImpl
import app.te.alo_chef.data.auth.data_source.remote.AuthRemoteDataSource
import app.te.alo_chef.data.auth.repository.AuthRepositoryImpl
import app.te.alo_chef.data.general.data_source.remote.GeneralRemoteDataSource
import app.te.alo_chef.data.general.repository.GeneralRepositoryImpl
import app.te.alo_chef.data.cart.CartDataSource
import app.te.alo_chef.data.checkout.data_source.CheckoutDataSource
import app.te.alo_chef.data.checkout.repository.CheckoutRepositoryImpl
import app.te.alo_chef.data.cart.CartRepositoryImpl
import app.te.alo_chef.data.home.data_source.remote.HomeRemoteDataSource
import app.te.alo_chef.data.home.repository.HomeRepositoryImpl
import app.te.alo_chef.data.intro.data_source.IntroRemoteDataSource
import app.te.alo_chef.data.intro.repository.IntroRepositoryImpl
import app.te.alo_chef.data.local.preferences.AppPreferences
import app.te.alo_chef.data.meal_details.data_source.MealDetailsDataSource
import app.te.alo_chef.data.meal_details.repository.MealDetailsRepositoryImpl
import app.te.alo_chef.data.my_locations.data_source.MyLocationsDataSource
import app.te.alo_chef.data.my_locations.repository.MyLocationsRepositoryImpl
import app.te.alo_chef.data.my_orders.data_source.OrdersDataSource
import app.te.alo_chef.data.my_orders.repository.OrdersRepositoryImpl
import app.te.alo_chef.data.profile.data_source.ProfileDataSource
import app.te.alo_chef.data.profile.repository.ProfileRepositoryImpl
import app.te.alo_chef.data.settings.data_source.remote.SettingsRemoteDataSource
import app.te.alo_chef.data.settings.repository.SettingsRepositoryImpl
import app.te.alo_chef.data.subscriptions.data_source.SubscriptionsDataSource
import app.te.alo_chef.data.subscriptions.repository.SubscriptionsRepositoryImpl
import app.te.alo_chef.data.wallet.data_source.WalletDataSource
import app.te.alo_chef.data.wallet.repository.WalletRepositoryImpl
import app.te.alo_chef.domain.account.repository.AccountRepository
import app.te.alo_chef.domain.auth.repository.AuthRepository
import app.te.alo_chef.domain.general.repository.GeneralRepository
import app.te.alo_chef.domain.home.repository.HomeRepository
import app.te.alo_chef.domain.cart.repository.CartRepository
import app.te.alo_chef.domain.checkout.repository.CheckoutRepository
import app.te.alo_chef.domain.intro.repository.IntroRepository
import app.te.alo_chef.domain.meal_details.repository.MealDetailsRepository
import app.te.alo_chef.domain.my_locations.repository.MyLocationsRepository
import app.te.alo_chef.domain.orders.repository.OrdersRepository
import app.te.alo_chef.domain.profile.repository.ProfileRepository
import app.te.alo_chef.domain.settings.repository.SettingsRepository
import app.te.alo_chef.domain.subscriptions.repository.SubscriptionsRepository
import app.te.alo_chef.domain.wallet.repository.WalletRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGeneralRepository(
        remoteDataSource: GeneralRemoteDataSource
    ): GeneralRepository =
        GeneralRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource,
        appPreferences: AppPreferences
    ): AuthRepository = AuthRepositoryImpl(remoteDataSource, appPreferences)


    @Provides
    @Singleton
    fun provideAccountRepository(
        remoteDataSource: AccountRemoteDataSource,
        appPreferences: AppPreferences
    ): AccountRepository = AccountRepositoryImpl(remoteDataSource, appPreferences)


    @Provides
    @Singleton
    fun provideHomeRepository(
        remoteDataSource: HomeRemoteDataSource
    ): HomeRepository = HomeRepositoryImpl(remoteDataSource)


    @Provides
    @Singleton
    fun provideIntroRepository(
        remoteDataSource: IntroRemoteDataSource
    ): IntroRepository = IntroRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        remoteDataSource: SettingsRemoteDataSource
    ): SettingsRepository = SettingsRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideUpdateProfileRepository(
        remoteDataSource: ProfileDataSource,
        appPreferences: AppPreferences
    ): ProfileRepository = ProfileRepositoryImpl(remoteDataSource, appPreferences)

    @Provides
    @Singleton
    fun provideMealDetailsRepository(
        remoteDataSource: MealDetailsDataSource
    ): MealDetailsRepository = MealDetailsRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideWalletRepository(
        remoteDataSource: WalletDataSource
    ): WalletRepository = WalletRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideOrdersRepository(
        remoteDataSource: OrdersDataSource
    ): OrdersRepository = OrdersRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideSubscriptionsRepository(
        remoteDataSource: SubscriptionsDataSource
    ): SubscriptionsRepository = SubscriptionsRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideMyLocationsRepository(
        remoteDataSource: MyLocationsDataSource,
        appPreferences: AppPreferences
    ): MyLocationsRepository = MyLocationsRepositoryImpl(remoteDataSource, appPreferences)

    @Provides
    @Singleton
    fun provideCheckoutRepository(
        remoteDataSource: CheckoutDataSource,
    ): CheckoutRepository = CheckoutRepositoryImpl(remoteDataSource)

}