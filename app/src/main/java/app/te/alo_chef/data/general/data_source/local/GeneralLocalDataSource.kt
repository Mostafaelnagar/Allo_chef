package app.te.alo_chef.data.general.data_source.local

import app.te.alo_chef.data.general.dto.config.GeneralConfig
import javax.inject.Inject

class GeneralLocalDataSource @Inject constructor(private val generalConfigDao: GeneralConfigDao) {

    fun getGeneralConfig() = generalConfigDao.getGeneralConfig()

    suspend fun addGeneralConfig(generalConfig: GeneralConfig) = generalConfigDao.addGeneralConfig(generalConfig)

    suspend fun emptyGeneralConfig() = generalConfigDao.emptyGeneralConfig()

}