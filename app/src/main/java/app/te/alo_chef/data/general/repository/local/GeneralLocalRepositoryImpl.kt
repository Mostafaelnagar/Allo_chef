package app.te.alo_chef.data.general.repository.local

import app.te.alo_chef.data.general.data_source.local.GeneralLocalDataSource
import app.te.alo_chef.data.general.dto.config.GeneralConfig
import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.cart.repository.CartRepository
import app.te.alo_chef.domain.general.repository.local.GeneralLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeneralLocalRepositoryImpl @Inject constructor(
    private val generalLocalDataSource: GeneralLocalDataSource
) : GeneralLocalRepository {

    override fun getGeneralConfig(): Flow<GeneralConfig> =
        generalLocalDataSource.getGeneralConfig()

    override suspend fun addToGeneralConfig(generalConfig: GeneralConfig) {
        generalLocalDataSource.getGeneralConfig()
        generalLocalDataSource.addGeneralConfig(generalConfig)
    }

}