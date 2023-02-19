package app.te.alo_chef.core

import androidx.room.Database
import androidx.room.RoomDatabase
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.data.cart.CartDao
import app.te.alo_chef.domain.cart.entity.MealCart

@Database(
    entities = [MealCart::class],
    version = 3, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract val getCartDao: CartDao
}

