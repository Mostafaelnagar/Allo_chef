package app.te.alo_chef.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.te.alo_chef.data.cart.CartDao
import app.te.alo_chef.data.general.data_source.local.Converters
import app.te.alo_chef.data.general.data_source.local.GeneralConfigDao
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import app.te.alo_chef.domain.cart.entity.MealCart

@Database(
    entities = [MealCart::class, GeneralConfig::class],
    version = 5, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val getCartDao: CartDao
    abstract val getGeneralConfig: GeneralConfigDao
}

