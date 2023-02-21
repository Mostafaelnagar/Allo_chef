package app.te.alo_chef.domain.checkout.use_case

import app.te.alo_chef.domain.checkout.repository.CheckoutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckPromoCodeUseCase @Inject constructor(private val repository: CheckoutRepository) {
    suspend fun checkPromoCode(code: String, dispatcher: CoroutineDispatcher) =
        withContext(dispatcher) {
            repository.checkPromoCode(code)
        }
}