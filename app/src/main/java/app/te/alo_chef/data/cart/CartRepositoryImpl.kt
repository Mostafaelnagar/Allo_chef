package app.te.alo_chef.data.cart

import app.te.alo_chef.domain.cart.entity.MealCart
import app.te.alo_chef.domain.cart.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDataSource: CartDataSource
) :
    CartRepository {

    override fun getCart(): Flow<List<MealCart>> =
        cartDataSource.getCart()

    override fun getCartItemsTotal(): Flow<String> =
        cartDataSource.getCartItemsTotal()

    override fun getCartCount(): Flow<Int> =
        cartDataSource.getCartCount()

    override fun getDeliveryDates(): Flow<List<String>> =
        cartDataSource.getDeliveryDates()


    override suspend fun addToCart(cart: MealCart) {
        val exits = cartDataSource.getIfMealExists(cart.product_id)
        if (exits == 0) {
            cart.priceAfter = (cart.priceAfter.toFloat() * cart.quantity).toString()
            cartDataSource.addToCart(cart)
        } else
            updateQuantityCart(cart.product_id, cart.quantity)
    }


    override suspend fun deleteFromCart(cartId: Int) =
        cartDataSource.deleteItem(cartId)

    override suspend fun updateQuantityCart(cartId: Int, quantity: Int) =
        cartDataSource.updateQuantity(cartId, quantity)

    override suspend fun deleteAll() = cartDataSource.emptyCart()

}