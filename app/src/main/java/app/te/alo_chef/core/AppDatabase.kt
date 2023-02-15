package app.te.alo_chef.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.te.alo_chef.data.home.data_source.dto.MealsData
import app.te.alo_chef.data.home.data_source.local.HomeDao
import app.te.alo_chef.domain.utils.Converters

@Database(
  entities = [MealsData::class],
  version = 2, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
  abstract val getHomeDao: HomeDao
}

