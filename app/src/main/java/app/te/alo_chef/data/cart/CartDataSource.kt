package app.te.alo_chef.data.cart

import app.te.alo_chef.domain.cart.entity.MealCart
import javax.inject.Inject

class CartDataSource @Inject constructor(private val cartDao: CartDao) {

    fun getCartCount() = cartDao.getCartTotalCount()

    fun getCart() = cartDao.getProducts()

    fun getDeliveryDates() = cartDao.getDeliveryDates()

    fun getCartItemsTotal() = cartDao.getCartTotal()

    suspend fun addToCart(meal: MealCart) = cartDao.addProduct(meal)

    suspend fun deleteItem(cartId: Int) = cartDao.deleteItem(cartId)

    suspend fun emptyCart() = cartDao.emptyProductCart()

    fun getIfMealExists(mealId: Int) = cartDao.getIfMealExists(mealId)

    fun updateQuantity(cartId: Int, quantity: Int) =
        cartDao.updateProductQuantity(productId = cartId, quantity = quantity)

}