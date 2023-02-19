package app.te.alo_chef.domain.cart.use_case

import app.te.alo_chef.domain.cart.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCartCountUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun getCartCount(dispatcher: CoroutineDispatcher) = withContext(dispatcher) {
        cartRepository.getCartCount()
    }
}