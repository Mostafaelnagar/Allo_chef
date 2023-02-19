package app.te.alo_chef.domain.cart.repository

import app.te.alo_chef.domain.cart.entity.MealCart
import kotlinx.coroutines.flow.Flow


interface CartRepository {
    fun getCart(): Flow<List<MealCart>>

    fun getCartCount(): Flow<Int>

    suspend fun addToCart(cart: MealCart)

    suspend fun deleteFromCart(cartId: Int)

    suspend fun updateQuantityCart(cartId: Int, quantity: Int)

    suspend fun deleteAll()
}