package app.te.alo_chef.domain.general.repository.local

import app.te.alo_chef.data.general.dto.config.GeneralConfig
import kotlinx.coroutines.flow.Flow


interface GeneralLocalRepository {
    fun getGeneralConfig(): Flow<GeneralConfig>
    suspend fun addToGeneralConfig(generalConfig: GeneralConfig)

}