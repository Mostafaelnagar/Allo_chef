package app.te.alo_chef.domain.cart.use_case

import app.te.alo_chef.domain.cart.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCartItemsTotalUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun getCartItemsTotal(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        cartRepository.getCartItemsTotal()
    }
}