package app.te.alo_chef.core.di.module

import android.content.Context
import androidx.room.Room
import app.te.alo_chef.core.AppDatabase
import app.te.alo_chef.data.cart.CartDataSource
import app.te.alo_chef.data.cart.CartRepositoryImpl
import app.te.alo_chef.domain.cart.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideMyDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "BuildConfig"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHomeLocalRepository(db: AppDatabase): CartDataSource {
        return CartDataSource(db.getCartDao)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDataSource: CartDataSource
    ): CartRepository = CartRepositoryImpl(cartDataSource)

}



