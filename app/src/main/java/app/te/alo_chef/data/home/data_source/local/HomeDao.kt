package app.te.alo_chef.data.home.data_source.local

import androidx.room.*
import app.te.alo_chef.data.home.data_source.dto.MealsData
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
  @Query("Select * from MealsData")
  fun getNews(): Flow<MealsData>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertHomeData(homeMainData: MealsData)
  @Query("DELETE from MealsData")
  fun deleteHomeData()

}