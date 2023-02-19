package app.te.alo_chef.domain.cart.use_case

import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.cart.repository.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    suspend fun addToCart(dispatcher: CoroutineDispatcher, cartMealCart: MealCart) =
        withContext(dispatcher) {
            cartRepository.addToCart(cartMealCart)
        }
}