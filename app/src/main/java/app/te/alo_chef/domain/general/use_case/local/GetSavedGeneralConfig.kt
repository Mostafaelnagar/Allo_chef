package app.te.alo_chef.domain.general.use_case.local

import app.te.alo_chef.domain.general.repository.local.GeneralLocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSavedGeneralConfig @Inject constructor(
    private val generalLocalRepository: GeneralLocalRepository
) {
    suspend fun getLocalConfig(dispatcher: CoroutineDispatcher) = with(dispatcher) {
        generalLocalRepository.getGeneralConfig()
    }
}