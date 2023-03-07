package app.te.alo_chef.domain.general.use_case

import app.te.alo_chef.domain.general.repository.GeneralRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGeneralConfig @Inject constructor(
    private val generalRepository: GeneralRepository
) {
    suspend operator fun invoke(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        generalRepository.getGeneralConfig()
    }
}