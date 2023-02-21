package app.te.alo_chef.domain.checkout.use_case

import app.te.alo_chef.domain.checkout.repository.CheckoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeliveryTimesUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend fun getDeliveryTimes(dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            repository.getDeliveryTimes()
        }
}