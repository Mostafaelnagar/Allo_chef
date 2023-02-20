package app.te.alo_chef.domain.cart.repository

import app.te.alo_chef.domain.cart.entity.MealCart
import kotlinx.coroutines.flow.Flow


interface CartRepository {
    fun getCart(): Flow<List<MealCart>>

    fun getCartItemsTotal(): Flow<String>

    fun getCartCount(): Flow<Int>

    fun getDeliveryDates(): Flow<List<String>>

    suspend fun addToCart(cart: MealCart)

    suspend fun deleteFromCart(cartId: Int)

    suspend fun updateQuantityCart(cartId: Int, quantity: Int)

    suspend fun deleteAll()
}