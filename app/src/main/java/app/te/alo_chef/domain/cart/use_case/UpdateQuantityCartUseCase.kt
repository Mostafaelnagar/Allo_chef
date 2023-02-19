package app.te.alo_chef.domain.cart.use_case

import app.te.alo_chef.domain.cart.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateQuantityCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun updateQuantityCart(dispatcher: CoroutineDispatcher, cartId: Int, quantity: Int) =
        withContext(dispatcher) {
            cartRepository.updateQuantityCart(cartId, quantity)
        }
}