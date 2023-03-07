package app.te.alo_chef.data.general.data_source.local

import androidx.room.*
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralConfigDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGeneralConfig(generalConfig: GeneralConfig)

    @Query("select * from GeneralConfig")
    fun getGeneralConfig(): Flow<GeneralConfig>

    @Query("DELETE FROM GeneralConfig")
    suspend fun emptyGeneralConfig()

}