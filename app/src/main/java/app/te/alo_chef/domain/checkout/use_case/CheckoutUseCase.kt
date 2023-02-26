package app.te.alo_chef.domain.checkout.use_case

import app.te.alo_chef.domain.checkout.entity.NewOrderRequest
import app.te.alo_chef.domain.checkout.repository.CheckoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckoutUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend fun checkout(newOrderRequest: NewOrderRequest, dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            repository.checkout(newOrderRequest)
        }
}