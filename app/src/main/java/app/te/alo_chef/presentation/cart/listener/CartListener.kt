package app.te.alo_chef.presentation.cart.listener

import app.te.alo_chef.presentation.base.events.BaseEventListener

interface CartListener : BaseEventListener {
    fun updateProductQuantity(productId: Int, quantity: Int)
    fun deleteItem(roomId: Int)
    fun openCheckout()
}